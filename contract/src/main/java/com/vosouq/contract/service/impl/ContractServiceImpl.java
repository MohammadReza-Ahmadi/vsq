package com.vosouq.contract.service.impl;

import com.vosouq.commons.model.BinaryDataHolder;
import com.vosouq.commons.model.kafka.ContractPayload;
import com.vosouq.commons.model.kafka.ContractProgressStatus;
import com.vosouq.commons.model.kafka.ObligatedParty;
import com.vosouq.commons.service.producer.ContractPaymentsKafkaProducer;
import com.vosouq.commons.service.producer.ContractsKafkaProducer;
import com.vosouq.commons.util.MessageUtil;
import com.vosouq.contract.controller.mapper.FileAddressMapper;
import com.vosouq.contract.controller.mapper.PaymentMapper;
import com.vosouq.contract.exception.*;
import com.vosouq.contract.model.*;
import com.vosouq.contract.repository.*;
import com.vosouq.contract.service.*;
import com.vosouq.contract.service.feign.BookkeepingServiceClient;
import com.vosouq.contract.service.feign.ProfileServiceClient;
import com.vosouq.contract.utills.MessageConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static com.vosouq.contract.controller.mapper.PaymentMapper.convertToPaymentPayload;
import static com.vosouq.contract.utills.MessageConstants.CONTRACT_SIGNED_BY_BUYER;
import static com.vosouq.contract.utills.MessageConstants.CONTRACT_SIGNED_BY_SELLER;
import static com.vosouq.contract.utills.TimeUtil.*;

@Service
@Transactional
@Slf4j
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final VehicleContractRepository vehicleContractRepository;
    private final CommodityContractRepository commodityContractRepository;
    private final ServiceContractRepository serviceContractRepository;
    private final PaymentService paymentService;
    private final SuggestionService suggestionService;
    private final FileAddressService fileAddressService;
    private final ContractTemplateService contractTemplateService;
    private final ContractDeliveryService contractDeliveryService;
    private final ContractBuyerApprovalService contractBuyerApprovalService;
    private final VehicleAttachmentService vehicleAttachmentService;
    private final ProfileServiceClient profileServiceClient;
    private final DealHistoryRepository dealHistoryRepository;
    private final MessageService messageService;
    private final NotificationService notificationService;
    private final BookkeepingServiceClient bookkeepingServiceClient;
    private final ContractsKafkaProducer contractsKafkaProducer;
    private final ContractPaymentsKafkaProducer contractPaymentsKafkaProducer;

    @Autowired
    public ContractServiceImpl(ContractRepository contractRepository,
                               VehicleContractRepository vehicleContractRepository,
                               CommodityContractRepository commodityContractRepository,
                               ServiceContractRepository serviceContractRepository,
                               PaymentService paymentService,
                               SuggestionService suggestionService,
                               FileAddressService fileAddressService,
                               ContractTemplateService contractTemplateService,
                               ContractDeliveryService contractDeliveryService,
                               ContractBuyerApprovalService contractBuyerApprovalService,
                               VehicleAttachmentService vehicleAttachmentService,
                               ProfileServiceClient profileServiceClient,
                               DealHistoryRepository dealHistoryRepository,
                               MessageService messageService,
                               NotificationService notificationService,
                               BookkeepingServiceClient bookkeepingServiceClient,
                               @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
                                       ContractsKafkaProducer contractsKafkaProducer,
                               @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
                                       ContractPaymentsKafkaProducer contractPaymentsKafkaProducer
    ) {

        this.contractRepository = contractRepository;
        this.vehicleContractRepository = vehicleContractRepository;
        this.commodityContractRepository = commodityContractRepository;
        this.serviceContractRepository = serviceContractRepository;
        this.paymentService = paymentService;
        this.suggestionService = suggestionService;
        this.fileAddressService = fileAddressService;
        this.contractTemplateService = contractTemplateService;
        this.contractDeliveryService = contractDeliveryService;
        this.contractBuyerApprovalService = contractBuyerApprovalService;
        this.vehicleAttachmentService = vehicleAttachmentService;
        this.profileServiceClient = profileServiceClient;
        this.dealHistoryRepository = dealHistoryRepository;
        this.messageService = messageService;
        this.notificationService = notificationService;
        this.bookkeepingServiceClient = bookkeepingServiceClient;
        this.contractsKafkaProducer = contractsKafkaProducer;
        this.contractPaymentsKafkaProducer = contractPaymentsKafkaProducer;
    }

    @Override
    public Contract confirmAndSaveCommodity(Long contractTemplateId,
                                            Long suggestionId,
                                            String title,
                                            Long onlineUserId,
                                            Long price,
                                            Long terminationPenalty,
                                            Timestamp deliveryDate,
                                            String province,
                                            String city,
                                            String neighbourhood,
                                            String description,
                                            List<PaymentTemplateModel> paymentTemplates,
                                            List<Long> fileIds,
                                            Long pricePerUnit,
                                            Integer quantity,
                                            Unit unit,
                                            List<Long> otherAttachments,
                                            String signDescription) {

        ContractTemplate contractTemplate = contractTemplateService.find(contractTemplateId);

        if (!contractTemplate.getSellerId().equals(onlineUserId))
            throw new IncompatibleValueException();

        if (!(contractTemplate instanceof CommodityContractTemplate))
            throw new IncompatibleValueException();

        if (contractTemplate.getNumberOfRepeats() < 1)
            throw new ContractNumberOfRepeatsExceededException();

        contractTemplateService.minusNumberOfRepeats(contractTemplate);

        Suggestion confirmedSuggestion = suggestionService.confirm(suggestionId, contractTemplateId);

        CommodityContract contract = buildCommodity(pricePerUnit, quantity, unit);

        setCommonContractFields(
                contract,
                contractTemplate,
                confirmedSuggestion,
                onlineUserId,
                title,
                price,
                terminationPenalty,
                province,
                city,
                neighbourhood,
                description,
                otherAttachments,
                signDescription);

        contract = commodityContractRepository.save(contract);

        setContractRelatedEntities(contract, deliveryDate, paymentTemplates, onlineUserId, fileIds);

        makeDealHistory(
                contract.getId(),
                "CONTRACT",
                contract.getSellerId(),
                contract.getBuyerId(),
                Action.SIGN_BY_SELLER,
                ActionState.WAITING_FOR_SIGN,
                fromMillis(nowInMillis() + (60 * 60 * 1000)),
                contract.getTitle());

        messageService.send(
                contract.getBuyerId(),
                MessageUtil.getMessage(MessageConstants.SUGGESTION_APPROVED_MESSAGE, contract.getTitle()),
                contract.getId(),
                "CONTRACT",
                SubjectSubType.SUGGESTION_APPROVED
        );

        sendPushAndMessage(
                contract.getBuyerId(),
                contract.getId(),
                "CONTRACT",
                CONTRACT_SIGNED_BY_SELLER,
                contract.getTitle());

        return commodityContractRepository.save(contract);
    }

    @Override
    public Contract confirmAndSaveVehicle(Long contractTemplateId,
                                          Long suggestionId,
                                          String title,
                                          Long sellerId,
                                          Long price,
                                          Long terminationPenalty,
                                          Timestamp deliveryDate,
                                          String province,
                                          String city,
                                          String neighbourhood,
                                          String description,
                                          List<PaymentTemplateModel> paymentTemplates,
                                          List<Long> fileIds,
                                          Integer manufactureYear,
                                          Integer usage,
                                          Gearbox gearbox,
                                          Fuel fuel,
                                          BodyStatus bodyStatus,
                                          Color color,
                                          VehicleAttachmentModel vehicleAttachmentModel,
                                          Boolean isOwner,
                                          List<Long> vehicleAttorneyAttachments,
                                          List<Long> otherAttachments,
                                          String signDescription) {

        ContractTemplate contractTemplate = contractTemplateService.find(contractTemplateId);

        if (!contractTemplate.getSellerId().equals(sellerId))
            throw new IncompatibleValueException();

        if (!(contractTemplate instanceof VehicleContractTemplate))
            throw new IncompatibleValueException();

        if (contractTemplate.getNumberOfRepeats() < 1)
            throw new ContractNumberOfRepeatsExceededException();

        contractTemplateService.minusNumberOfRepeats(contractTemplate);

        Suggestion confirmedSuggestion = suggestionService.confirm(suggestionId, contractTemplateId);

        VehicleContract contract = buildVehicle(
                manufactureYear,
                usage,
                gearbox,
                fuel,
                bodyStatus,
                color,
                isOwner);

        setCommonContractFields(
                contract,
                contractTemplate,
                confirmedSuggestion,
                sellerId,
                title,
                price,
                terminationPenalty,
                province,
                city,
                neighbourhood,
                description,
                otherAttachments,
                signDescription);

        contract = vehicleContractRepository.save(contract);

        setContractRelatedEntities(contract, deliveryDate, paymentTemplates, sellerId, fileIds);

        contract.setVehicleAttachment(vehicleAttachmentService.save(vehicleAttachmentModel, sellerId));

        if (!CollectionUtils.isEmpty(vehicleAttorneyAttachments)) {
            contract.setAttorneyAttachments(
                    vehicleAttorneyAttachments.stream()
                            .map(attachment -> fileAddressService.findAndValidate(attachment, FileType.ATTACHMENT_FILE, sellerId))
                            .collect(Collectors.toList()));
        }

        makeDealHistory(
                contract.getId(),
                "CONTRACT",
                contract.getSellerId(),
                contract.getBuyerId(),
                Action.SIGN_BY_SELLER,
                ActionState.WAITING_FOR_SIGN,
                fromMillis(nowInMillis() + (60 * 60 * 1000)),
                contract.getTitle());

        messageService.send(
                contract.getBuyerId(),
                MessageUtil.getMessage(MessageConstants.SUGGESTION_APPROVED_MESSAGE, contract.getTitle()),
                contract.getId(),
                "CONTRACT",
                SubjectSubType.SUGGESTION_APPROVED
        );

        sendPushAndMessage(
                contract.getBuyerId(),
                contract.getId(),
                "CONTRACT",
                CONTRACT_SIGNED_BY_SELLER,
                contract.getTitle());

        return vehicleContractRepository.save(contract);
    }

    @Override
    public Contract confirmAndSaveService(Long contractTemplateId,
                                          Long suggestionId,
                                          String title,
                                          Long sellerId,
                                          Long price,
                                          Long terminationPenalty,
                                          Timestamp deliveryDate,
                                          String province,
                                          String city,
                                          String neighbourhood,
                                          String description,
                                          List<PaymentTemplateModel> paymentTemplates,
                                          List<Long> fileIds,
                                          List<Long> otherAttachments,
                                          String signDescription) {

        ContractTemplate contractTemplate = contractTemplateService.find(contractTemplateId);

        if (!contractTemplate.getSellerId().equals(sellerId))
            throw new IncompatibleValueException();

        if (!(contractTemplate instanceof ServiceContractTemplate))
            throw new IncompatibleValueException();

        if (contractTemplate.getNumberOfRepeats() < 1)
            throw new ContractNumberOfRepeatsExceededException();

        contractTemplateService.minusNumberOfRepeats(contractTemplate);

        Suggestion confirmedSuggestion = suggestionService.confirm(suggestionId, contractTemplateId);

        ServiceContract contract = new ServiceContract();

        setCommonContractFields(
                contract,
                contractTemplate,
                confirmedSuggestion,
                sellerId,
                title,
                price,
                terminationPenalty,
                province,
                city,
                neighbourhood,
                description,
                otherAttachments,
                signDescription);

        contract = serviceContractRepository.save(contract);

        setContractRelatedEntities(contract, deliveryDate, paymentTemplates, sellerId, fileIds);

        makeDealHistory(
                contract.getId(),
                "CONTRACT",
                contract.getSellerId(),
                contract.getBuyerId(),
                Action.SIGN_BY_SELLER,
                ActionState.WAITING_FOR_SIGN,
                fromMillis(nowInMillis() + (60 * 60 * 1000)),
                contract.getTitle());

        messageService.send(
                contract.getBuyerId(),
                MessageUtil.getMessage(MessageConstants.SUGGESTION_APPROVED_MESSAGE, contract.getTitle()),
                contract.getId(),
                "CONTRACT",
                SubjectSubType.SUGGESTION_APPROVED
        );

        sendPushAndMessage(
                contract.getBuyerId(),
                contract.getId(),
                "CONTRACT",
                CONTRACT_SIGNED_BY_SELLER,
                contract.getTitle());

        return serviceContractRepository.save(contract);
    }

    @Override
    public Contract findById(Long id) {
        return contractRepository
                .findById(id)
                .orElseThrow(ContractNotFoundException::new);
    }

    @Override
    public BinaryDataHolder<User, User> getContractSides(Long contractId) {
        Contract contract = findById(contractId);
        User seller = profileServiceClient.findById(contract.getSellerId());
        User buyer = profileServiceClient.findById(contract.getBuyerId());
        return new BinaryDataHolder<>(seller, buyer);
    }

    @Override
    public List<Contract> findAll(Long userId) {
        return contractRepository.findBySellerIdOrBuyerIdOrderByUpdateDateDesc(userId, userId);
    }

    @Override
    public List<RecentDealModel> getRecentDeals(Long userId, Integer page, Integer pageSize) {

        Pageable pageable = PageRequest.of(page - 1, pageSize);

        List<DealHistory> dealHistories = dealHistoryRepository.findBySellerIdOrBuyerIdOrderByUpdateDateDesc(userId,
                userId, pageable);

        return dealHistories
                .stream()
                .map(RecentDealModel::fromDealHistory)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecentDealModel> getRecentDeals(Long userId,
                                                List<ActionState> filters,
                                                Integer page,
                                                Integer pageSize) {
        Pageable pageable = PageRequest.of(page - 1,
                pageSize,
                Sort.Direction.DESC,
                "updateDate");

        List<DealHistory> history = dealHistoryRepository.findByActionStateInAndSellerIdOrBuyerId(filters,
                userId,
                userId,
                pageable);
        return history.stream()
                .map(RecentDealModel::fromDealHistory)
                .collect(Collectors.toList());
    }

    @Override
    public List<ContractModel> findAllContractModels(Long userId,
                                                     Collection<ContractState> states,
                                                     Integer page,
                                                     Integer pageSize) {

        List<Contract> contracts =
                contractRepository.findBySellerIdOrBuyerIdAndStateInOrderByUpdateDateDesc(
                        userId,
                        userId,
                        states,
                        PageRequest.of(page - 1, pageSize));
        Map<Long, String> userMap = new HashMap<>();

        return contracts.stream()
                .map(contract -> map(contract, userId, userMap))
                .collect(Collectors.toList());
    }

    @Override
    public List<ContractModel> findAllContractModels(
            Long userId,
            String term,
            Collection<ContractState> states,
            Integer page,
            Integer pageSize) {
        Map<Long, String> userMap = new HashMap<>();
        List<ContractModel> models;
        if (term == null || term.isBlank()) {
            models = contractRepository.findBySellerIdOrBuyerIdAndStateInOrderByUpdateDateDesc(
                    userId,
                    userId,
                    states,
                    PageRequest.of(page - 1, pageSize)
            )
                    .stream()
                    .map(contract -> map(contract, userId, userMap))
                    .collect(Collectors.toList());
        } else {
            models = contractRepository.findBySellerIdOrBuyerIdAndTitleIsContainingAndStateInOrderByUpdateDateDesc(
                    userId,
                    userId,
                    term,
                    states,
                    PageRequest.of(page - 1, pageSize)
            )
                    .stream()
                    .map(contract -> map(contract, userId, userMap))
                    .collect(Collectors.toList());
        }

        return models;
    }

    @Override
    public List<ContractModel> findAllTurnovers(Long userId) {

        List<Contract> contracts = findAll(userId);
        Map<Long, String> userMap = new HashMap<>();

        return contracts.stream()
                .filter(contract -> contract.getPayments().stream().anyMatch(payment -> payment.getStatus().equals(PaymentStatus.PAID)))
                .map(contract -> map(contract, userId, userMap))
                .collect(Collectors.toList());
    }

    private ContractModel map(Contract contract, Long userId, Map<Long, String> userMap) {
        ContractModel model = new ContractModel();

        model.setId(contract.getId());
        model.setTitle(contract.getTitle());
        model.setUpdateDate(contract.getUpdateDate() == null ? null : contract.getUpdateDate().getTime());
        model.setActor(userId.equals(contract.getSellerId()) ? Actor.SELLER : Actor.BUYER);
        model.setPrice(contract.getPrice());

        try {
            model.setImageFile(
                    FileAddressMapper.map(contract.getFiles(), FileType.PRODUCT_IMAGE).get(0));
        } catch (IndexOutOfBoundsException exception) {
            log.error("did not find any product image for contract!", exception);
        }

        switch (contract.getState()) {
            case SIGN_BY_SELLER:
                model.setState(ContractState.WAIT_FOR_SIGNING);
                break;
            case PAYMENTS_IN_PROGRESS:
            case SIGN_BY_BUYER:
                model.setState(ContractState.PAYMENTS_IN_PROGRESS);
                break;
            case PAYMENTS_DONE:
            case DELIVERY_IN_PROGRESS:
                model.setState(ContractState.DELIVERY_IN_PROGRESS);
                break;
            case STALE:
                model.setState(ContractState.STALE);
                break;
            case DELIVERY_DONE:
            default:
                model.setState(ContractState.CONTRACT_END);

        }

        long inquiryUserId;

        if (userId.equals(contract.getSellerId())) {
            inquiryUserId = contract.getBuyerId();
        } else {
            inquiryUserId = contract.getSellerId();
        }

        try {
            if (!userMap.containsKey(inquiryUserId)) {
                User user = profileServiceClient.findById(inquiryUserId);
                model.setActorName(user.getName());
                userMap.put(inquiryUserId, user.getName());
            } else {
                model.setActorName(userMap.get(inquiryUserId));
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return model;
    }

    @Override
    public Timestamp signContractByBuyer(Long contractId, Long currentUserId) {

        Contract contract = findById(contractId);

        if (!contract.getBuyerId().equals(currentUserId)
                || !contract.getState().equals(ContractState.SIGN_BY_SELLER))
            throw new IncompatibleValueException();

        contract.setBuyerSignDate(nowInTimestamp());
        contract.setUpdateDate(nowInTimestamp());
        contract.setState(ContractState.SIGN_BY_BUYER);
        Suggestion contractSuggestion = contract.getSuggestion();
        contractSuggestion.setSuggestionState(SuggestionState.SIGNED);
        suggestionService.save(contractSuggestion);
        contract = save(contract);

        makeDealHistory(
                contract.getId(),
                "CONTRACT",
                contract.getSellerId(),
                contract.getBuyerId(),
                Action.SIGN_BY_BUYER,
                ActionState.SIGNED,
                nowInTimestamp(),
                contract.getTitle());
//TODO: externalize messages
        messageService.send(
                contract.getSellerId(),
                "قرارداد " + contract.getTitle() + " توسط خریدار امضا شد",
                contract.getId(),
                "CONTRACT",
                SubjectSubType.BUYER_SIGNED);

        notificationService.send(
                contract.getSellerId(),
                "قرارداد " + contract.getTitle() + " توسط خریدار امضا شد",
                contract.getId(),
                "CONTRACT");

        sendPushAndMessage(
                contract.getSellerId(),
                contract.getId(),
                "CONTRACT",
                CONTRACT_SIGNED_BY_BUYER,
                contract.getTitle());

        try {
            List<Payment> payments = contract.getPayments();

            makeDealHistory(
                    contract.getId(),
                    "CONTRACT",
                    contract.getSellerId(),
                    contract.getBuyerId(),
                    payments.size() > 1 ? Action.FIRST_STEP_PAYMENT : Action.PAY,
                    ActionState.WAITING_FOR_PAYMENT,
                    payments.get(0).getDueDate(),
                    contract.getTitle());

        } catch (Throwable throwable) {
            //TODO: @Siavash Investigate what is being done here that should be ignored!!!!
            throwable.printStackTrace();
        }

        if (contract.getPayments() != null && !contract.getPayments().isEmpty()) {
            sendOnKafka(contract, ContractProgressStatus.STARTED, ObligatedParty.BUYER, true);
            return contract.getPayments().get(0).getDueDate();
        } else {
            throw new IncompatibleValueException();
        }
    }

    @Override
    public void rejectContractByBuyer(Long contractId, Long userId) {
        if (contractId == null || userId == null)
            throw new IncompatibleValueException();

        Contract contract = findById(contractId);

        if (!contract.getBuyerId().equals(userId)
                || contract.getContractTemplate() == null
                || contract.getContractTemplate().getId() == null
                || !contract.getState().equals(ContractState.SIGN_BY_SELLER))
            throw new IncompatibleValueException();

        contract.setUpdateDate(nowInTimestamp());
        contract.setState(ContractState.REJECTED_BY_BUYER);
        Suggestion contractSuggestion = contract.getSuggestion();
        contractSuggestion.setSuggestionState(SuggestionState.DENIED);
        suggestionService.save(contractSuggestion);
        contract = contractRepository.save(contract);
        sendOnKafka(contract, ContractProgressStatus.DONE, ObligatedParty.NONE);
    }

    @Override
    public ContractStatusModel getContractStatus(Long contractId, Long userId) {
        if (contractId == null)
            throw new IncompatibleValueException();

        Contract contract = findById(contractId);

        if (contract.getBuyerId().equals(userId))
            return readContractStatusForBuyer(contract);
        else
            return readContractStatusForSeller(contract);


    }

    @Override
    public void doPayment(Long contractId, Long amount) {

        Contract contract = findById(contractId);

        Payment currentPayment = paymentService.getCurrentPayment(contract);

        if (!currentPayment.getAmount().equals(amount))
            throw new CannotPayPaymentException();

        paymentService.doPayment(currentPayment);

        if (paymentService.isCurrentPaymentTheLastOne(contract, currentPayment)) {
            contract.setState(ContractState.PAYMENTS_DONE);

            List<Payment> payments = contract.getPayments().stream().sorted(Comparator.comparingLong(Payment::getId)).collect(Collectors.toList());

            Action action = Action.PAY;

            if (payments.size() > 1) {
                if (payments.indexOf(currentPayment) == 1)
                    action = Action.SECOND_STEP_PAYMENT;
                else if (payments.indexOf(currentPayment) == 2)
                    action = Action.THIRD_STEP_PAYMENT;
            }

            makeDealHistory(
                    contract.getId(),
                    "CONTRACT",
                    contract.getSellerId(),
                    contract.getBuyerId(),
                    action,
                    ActionState.PAID,
                    nowInTimestamp(),
                    contract.getTitle());

            makeDealHistory(
                    contract.getId(),
                    "CONTRACT",
                    contract.getSellerId(),
                    contract.getBuyerId(),
                    Action.DELIVER,
                    ActionState.WAITING_FOR_DELIVERY,
                    contract.getDelivery().getDueDate(),
                    contract.getTitle());
//TODO: externalize messages
            messageService.send(
                    contract.getSellerId(),
                    "پرداخت قرارداد " + contract.getTitle() + " تکمیل شد. در انتظار تحویل توسط فروشنده",
                    contract.getId(),
                    "CONTRACT",
                    SubjectSubType.EMPTY);

            notificationService.send(
                    contract.getSellerId(),
                    "پرداخت قرارداد " + contract.getTitle() + " تکمیل شد. در انتظار تحویل توسط فروشنده",
                    contract.getId(),
                    "CONTRACT");

            messageService.send(
                    contract.getBuyerId(),
                    "پرداخت قرارداد " + contract.getTitle() + " تکمیل شد. در انتظار تحویل توسط فروشنده",
                    contract.getId(),
                    "CONTRACT",
                    SubjectSubType.EMPTY);
            sendOnKafka(currentPayment);
            sendOnKafka(contract, ContractProgressStatus.ONGOING, ObligatedParty.SELLER);
        } else {
            contract.setState(ContractState.PAYMENTS_IN_PROGRESS);

            List<Payment> payments = contract.getPayments().stream().sorted(Comparator.comparingLong(Payment::getId))
                    .collect(Collectors.toList());

            int step = payments.indexOf(currentPayment);
            switch (step) {
                case 0: {
                    makeDealHistory(
                            contract.getId(),
                            "CONTRACT",
                            contract.getSellerId(),
                            contract.getBuyerId(),
                            Action.FIRST_STEP_PAYMENT,
                            ActionState.PAID,
                            nowInTimestamp(),
                            contract.getTitle());

                    messageService.send(
                            contract.getSellerId(),
                            "پرداخت مرحله اول " + contract.getTitle() + " انجام شد",
                            contract.getId(),
                            "CONTRACT",
                            SubjectSubType.EMPTY);

                    notificationService.send(
                            contract.getSellerId(),
                            "پرداخت مرحله اول " + contract.getTitle() + " انجام شد",
                            contract.getId(),
                            "CONTRACT");

                    makeDealHistory(
                            contract.getId(),
                            "CONTRACT",
                            contract.getSellerId(),
                            contract.getBuyerId(),
                            Action.SECOND_STEP_PAYMENT,
                            ActionState.WAITING_FOR_PAYMENT,
                            payments.get(1).getDueDate(),
                            contract.getTitle());

                    break;
                }
                case 1: {
                    makeDealHistory(
                            contract.getId(),
                            "CONTRACT",
                            contract.getSellerId(),
                            contract.getBuyerId(),
                            Action.SECOND_STEP_PAYMENT,
                            ActionState.PAID,
                            nowInTimestamp(),
                            contract.getTitle());

                    messageService.send(
                            contract.getSellerId(),
                            "پرداخت مرحله دوم " + contract.getTitle() + " انجام شد",
                            contract.getId(),
                            "CONTRACT",
                            SubjectSubType.EMPTY);

                    notificationService.send(
                            contract.getSellerId(),
                            "پرداخت مرحله دوم " + contract.getTitle() + " انجام شد",
                            contract.getId(),
                            "CONTRACT");

                    makeDealHistory(
                            contract.getId(),
                            "CONTRACT",
                            contract.getSellerId(),
                            contract.getBuyerId(),
                            Action.THIRD_STEP_PAYMENT,
                            ActionState.WAITING_FOR_PAYMENT,
                            payments.get(2).getDueDate(),
                            contract.getTitle());
                    break;
                }
            }
            sendOnKafka(paymentService.findById(currentPayment.getId()));
        }
        contract.setUpdateDate(nowInTimestamp());
        save(contract);
    }

    @Override
    public long paymentInquiry(Long userId, Long contractId) {
        Contract contract = findById(contractId);

        if (!contract.getBuyerId().equals(userId))
            throw new ContractNotFoundException();

        List<Payment> payments = contract.getPayments();
        List<Payment> sortedPayments = payments
                .stream()
                .sorted(Comparator.comparingLong(Payment::getId))
                .collect(Collectors.toList());

        for (Payment payment : sortedPayments) {
            if (payment.getStatus().equals(PaymentStatus.UNPAID))
                return payment.getAmount();
        }

        return 0;
    }

    @Override
    public void depositContractFee(Long contractId,
                                   Boolean success,
                                   Long depositDate) {
        if (success) {
            Contract contract = findById(contractId);
            if (!contract.getState().equals(ContractState.BUYER_APPROVAL))
                throw new CannotDepositContractFeeException();

            contract.setUpdateDate(nowInTimestamp());
            contract.setFeeDepositDate(fromMillis(depositDate));
            contract.setState(ContractState.FEE_DEPOSIT_END);
            save(contract);
        }
    }

    @Override
    public void deliverContract(Long contractId, Long buyerId) {
        Contract contract = findById(contractId);

        if (!contract.getBuyerId().equals(buyerId) && !contract.getState().equals(ContractState.PAYMENTS_DONE))
            throw new CannotDeliverContractSubjectException();

        contractDeliveryService.deliverContractSubject(contract.getDelivery());
        contract.setState(ContractState.DELIVERY_DONE);
        save(contract);

        makeDealHistory(
                contract.getId(),
                "CONTRACT",
                contract.getSellerId(),
                contract.getBuyerId(),
                Action.DELIVER,
                ActionState.DELIVERED,
                nowInTimestamp(),
                contract.getTitle());

        messageService.send(
                contract.getBuyerId(),
                contract.getTitle() + " توسط فروشنده تحویل داده شد",
                contract.getId(),
                "CONTRACT",
                SubjectSubType.EMPTY);

        messageService.send(
                contract.getSellerId(),
                MessageUtil.getMessage(MessageConstants.CONTRACT_DELIVERED_MESSAGE),
                contract.getId(),
                "CONTRACT",
                SubjectSubType.DELIVERED);

        notificationService.send(
                contract.getBuyerId(),
                contract.getTitle() + " توسط فروشنده تحویل داده شد",
                contract.getId(),
                "CONTRACT");

        messageService.send(
                contract.getSellerId(),
                "مبلغ قرارداد " + contract.getTitle() + " به حساب وثوق شما منتقل شد",
                contract.getId(),
                "CONTRACT",
                SubjectSubType.EMPTY);

        notificationService.send(
                contract.getSellerId(),
                "مبلغ قرارداد " + contract.getTitle() + " به حساب وثوق شما منتقل شد",
                contract.getId(),
                "CONTRACT");

        bookkeepingServiceClient.settle(contract.getId(), contract.getSellerId());
        sendOnKafka(contract, ContractProgressStatus.DONE, ObligatedParty.NONE);
    }

    // todo : just for testing purposes!! should be completed Later after Analysis
    @Override
    public void sealOfApprovalByBuyer(Long contractId,
                                      Long buyerId,
                                      Boolean isApproved) {

        Contract contract = findById(contractId);

        if (!contract.getBuyerId().equals(buyerId)
                || !contract.getState().equals(ContractState.DELIVERY_DONE))
            throw new CannotApproveContractSubjectDeliveryException();

        contractBuyerApprovalService.sealOfApproval(contract.getBuyerApproval(), isApproved);
        contract.setUpdateDate(nowInTimestamp());
        if (isApproved) {
            contract.setState(ContractState.BUYER_APPROVAL);
            save(contract);
        }
    }

    private void makeDealHistory(Long subjectId,
                                 @SuppressWarnings("SameParameterValue") String subjectType,
                                 Long sellerId,
                                 Long buyerId,
                                 Action action,
                                 ActionState actionState,
                                 Timestamp actionDate,
                                 String title) {

        Optional<DealHistory> dealHistoryOptional = dealHistoryRepository.findFirstBySubjectIdOrderByActionDateDesc(subjectId);

        if (dealHistoryOptional.isPresent()) {
            DealHistory deal = dealHistoryOptional.get();
            if (deal.getActionState().toString().toLowerCase().contains("wait")) {

                deal.setAction(action);
                deal.setActionState(actionState);
                deal.setActionDate(actionDate);
                deal.setUpdateDate(nowInTimestamp());

                dealHistoryRepository.saveAndFlush(deal);

                return;
            }
        }

        DealHistory dealHistory = new DealHistory();
        dealHistory.setSubjectId(subjectId);
        dealHistory.setSubjectType(subjectType);
        dealHistory.setSellerId(sellerId);
        dealHistory.setBuyerId(buyerId);
        dealHistory.setAction(action);
        dealHistory.setActionState(actionState);
        dealHistory.setActionDate(actionDate);
        dealHistory.setTitle(title);
        dealHistory.setCreateDate(nowInTimestamp());
        dealHistory.setUpdateDate(nowInTimestamp());

        dealHistoryRepository.saveAndFlush(dealHistory);

    }

    // todo : question_1 : how long after seller sign, buyer can sign ??
    // todo : question_2 : how long after delivery, buyer can show approval ??
    // todo : question_3 : how long after delivery, Money deposit should happen ??
    private ContractStatusModel readContractStatusForSeller(Contract contract) {

        List<ContractStepModel> steps = new ArrayList<>();

        ContractState currentState = contract.getState();
        Timestamp contractDeliveryDate = contract.getDelivery().getDueDate();
        contract.getPayments().sort(Comparator.comparing(Payment::getId));
        Payment firstPayment = contract.getPayments().get(0);
        Payment secondPayment = null;
        Payment thirdPayment = null;
        ContractStepModel secondPaymentStep = null;
        ContractStepModel thirdPaymentStep = null;
        int paymentsCount = contract.getMultiStepPayment() ? contract.getPayments().size() : 1;
        if (paymentsCount > 1) {
            secondPayment = contract.getPayments().get(1);
            if (paymentsCount > 2) {
                thirdPayment = contract.getPayments().get(2);
            }
        }

        ContractStatusModel contractStatus = new ContractStatusModel();
        contractStatus.setContractId(contract.getId());
        contractStatus.setSellerId(contract.getSellerId());
        contractStatus.setBuyerId(contract.getBuyerId());
        contractStatus.setNow(nowInTimestamp());
        contractStatus.setPaymentsCount(paymentsCount);

        ContractStepModel signStep = new ContractStepModel(
                MessageUtil.getMessage("SIGN_TITLE"),
                ContractState.SIGN_BY_BUYER,
                Actor.BUYER,
                Actor.BUYER,
                Actor.SELLER,
                null,
                getOneHourLater(contract.getSellerSignDate()),
                null,
                ContractStepStatus.WAIT);


        ContractStepModel firstPaymentStep;
        if (contract.getMultiStepPayment()) {
            firstPaymentStep = new ContractStepModel(
                    MessageUtil.getMessage("FIRST_PAYMENT_TITLE_MULTISTEP"),
                    secondPayment == null ? ContractState.PAYMENTS_DONE : ContractState.PAYMENTS_IN_PROGRESS,
                    Actor.BUYER,
                    Actor.BUYER,
                    Actor.VOSOUQ,
                    firstPayment.getAmount(),
                    firstPayment.getDueDate(),
                    firstPayment.getPaymentDate(),
                    ContractStepStatus.WAIT);
        } else {
            firstPaymentStep = new ContractStepModel(
                    MessageUtil.getMessage("FIRST_PAYMENT_TITLE"),
                    secondPayment == null ? ContractState.PAYMENTS_DONE : ContractState.PAYMENTS_IN_PROGRESS,
                    Actor.BUYER,
                    Actor.BUYER,
                    Actor.VOSOUQ,
                    firstPayment.getAmount(),
                    firstPayment.getDueDate(),
                    firstPayment.getPaymentDate(),
                    ContractStepStatus.WAIT);
        }

        if (secondPayment != null) {
            secondPaymentStep = new ContractStepModel(
                    MessageUtil.getMessage("SECOND_PAYMENT_TITLE"),
                    thirdPayment == null ? ContractState.PAYMENTS_DONE : ContractState.PAYMENTS_IN_PROGRESS,
                    Actor.BUYER,
                    Actor.BUYER,
                    Actor.VOSOUQ,
                    secondPayment.getAmount(),
                    secondPayment.getDueDate(),
                    secondPayment.getPaymentDate(),
                    ContractStepStatus.WAIT);
        }

        if (thirdPayment != null) {
            thirdPaymentStep = new ContractStepModel(
                    MessageUtil.getMessage("THIRD_PAYMENT_TITLE"),
                    ContractState.PAYMENTS_DONE,
                    Actor.BUYER,
                    Actor.BUYER,
                    Actor.VOSOUQ,
                    thirdPayment.getAmount(),
                    thirdPayment.getDueDate(),
                    thirdPayment.getPaymentDate(),
                    ContractStepStatus.WAIT);
        }

        ContractStepModel deliveryStep = new ContractStepModel(
                MessageUtil.getMessage("DELIVERY_TITLE_FOR_SELLER"),
                ContractState.DELIVERY_IN_PROGRESS,
                Actor.SELLER,
                Actor.BUYER,
                Actor.SELLER,
                null,
                contract.getDelivery().getDueDate(),
                contract.getDelivery().getDeliveryDate(),
                ContractStepStatus.WAIT);

        ContractStepModel buyerApprovalStep = new ContractStepModel(
                MessageUtil.getMessage("BUYER_APPROVAL_TITLE"),
                ContractState.BUYER_APPROVAL,
                Actor.BUYER,
                Actor.BUYER,
                Actor.VOSOUQ,
                null,
                null,
                contract.getBuyerApproval().getApproveDate(),
                ContractStepStatus.WAIT);

        ContractStepModel feeDepositState = new ContractStepModel(
                MessageUtil.getMessage("FEE_DEPOSIT_TITLE"),
                ContractState.FEE_DEPOSIT_END,
                Actor.VOSOUQ,
                Actor.VOSOUQ,
                Actor.SELLER,
                null,
                null,
                contract.getDelivery().getDeliveryDate(),
                ContractStepStatus.WAIT);

        ContractStepModel contractEndStep = new ContractStepModel(
                MessageUtil.getMessage("CONTRACT_END_TITLE"),
                ContractState.CONTRACT_END,
                Actor.NONE,
                Actor.NONE,
                Actor.NONE,
                null,
                null,
                contract.getDelivery().getDeliveryDate(),
                ContractStepStatus.WAIT);

        if (currentState.equals(ContractState.SIGN_BY_SELLER)) {
            contractStatus.setCurrentActionDueDate(getOneHourLater(contract.getSellerSignDate()));
            contractStatus.setAction(Action.NONE);
            contractStatus.setMessages(new String[]{MessageUtil.getMessage("SIGN_BY_SELLER_FOR_SELLER_1"),
                    MessageUtil.getMessage("SIGN_BY_SELLER_FOR_SELLER_2")});
            contractStatus.setTimerMessage(MessageUtil.getMessage("SIGN_BY_SELLER_FOR_TIMER"));

            signStep.setStepStatus(ContractStepStatus.CURRENT);
            steps.add(signStep);

            steps.add(firstPaymentStep);
            if (secondPaymentStep != null) steps.add(secondPaymentStep);
            if (thirdPaymentStep != null) steps.add(thirdPaymentStep);
            steps.add(deliveryStep);
            steps.add(buyerApprovalStep);
            steps.add(feeDepositState);
            steps.add(contractEndStep);

        } else {
            signStep.setStepStatus(ContractStepStatus.DONE);
            signStep.setActionDate(contract.getBuyerSignDate());
            steps.add(signStep);

            if (currentState.equals(ContractState.SIGN_BY_BUYER)) {
                contractStatus.setCurrentActionDueDate(firstPayment.getDueDate());
                contractStatus.setAction(Action.NONE);
                contractStatus.setMessages(new String[]{MessageUtil.getMessage("FIRST_PAYMENT_FOR_SELLER_1"),
                        MessageUtil.getMessage("FIRST_PAYMENT_FOR_SELLER_2")});
                contractStatus.setTimerMessage(MessageUtil.getMessage("FIRST_PAYMENT_FOR_TIMER"));

                firstPaymentStep.setStepStatus(ContractStepStatus.CURRENT);
                steps.add(firstPaymentStep);

                if (secondPaymentStep != null) steps.add(secondPaymentStep);
                if (thirdPaymentStep != null) steps.add(thirdPaymentStep);
                steps.add(deliveryStep);
                steps.add(buyerApprovalStep);
                steps.add(feeDepositState);
                steps.add(contractEndStep);

            } else {
                firstPaymentStep.setStepStatus(ContractStepStatus.DONE);
                firstPaymentStep.setActionDate(firstPayment.getPaymentDate());
                steps.add(firstPaymentStep);

                if (currentState.equals(ContractState.PAYMENTS_IN_PROGRESS)) {
                    if (secondPaymentStep != null) {
                        if (secondPayment.getPaymentDate() == null && secondPayment.getStatus().equals(PaymentStatus.UNPAID)) {
                            contractStatus.setCurrentActionDueDate(secondPayment.getDueDate());
                            contractStatus.setAction(Action.NONE);
                            contractStatus.setMessages(
                                    new String[]{
                                            MessageUtil.getMessage("SECOND_PAYMENT_FOR_SELLER_1",
                                                    String.format("%,d", firstPayment.getAmount())),
                                            MessageUtil.getMessage("SECOND_PAYMENT_FOR_SELLER_2")});
                            contractStatus.setTimerMessage(MessageUtil.getMessage("SECOND_PAYMENT_FOR_TIMER"));

                            secondPaymentStep.setStepStatus(ContractStepStatus.CURRENT);
                            steps.add(secondPaymentStep);
                            if (thirdPaymentStep != null) steps.add(thirdPaymentStep);
                        } else {
                            secondPaymentStep.setStepStatus(ContractStepStatus.DONE);
                            steps.add(secondPaymentStep);
                        }
                    }

                    if (thirdPaymentStep != null) {
                        if (secondPaymentStep != null && !secondPaymentStep.getStepStatus().equals(ContractStepStatus.CURRENT)) {
                            if (thirdPayment.getPaymentDate() == null && thirdPayment.getStatus().equals(PaymentStatus.UNPAID)) {
                                contractStatus.setCurrentActionDueDate(thirdPayment.getDueDate());
                                contractStatus.setAction(Action.NONE);
                                contractStatus.setMessages(
                                        new String[]{
                                                MessageUtil.getMessage("THIRD_PAYMENT_FOR_SELLER_1",
                                                        String.format("%,d", secondPayment.getAmount())),
                                        MessageUtil.getMessage("THIRD_PAYMENT_FOR_SELLER_2")});
                                contractStatus.setTimerMessage(MessageUtil.getMessage("THIRD_PAYMENT_FOR_TIMER"));

                                thirdPaymentStep.setStepStatus(ContractStepStatus.CURRENT);
                                steps.add(thirdPaymentStep);
                            } else {
                                thirdPaymentStep.setStepStatus(ContractStepStatus.DONE);
                                steps.add(thirdPaymentStep);
                            }
                        }
                    }

                    steps.add(deliveryStep);
                    steps.add(buyerApprovalStep);
                    steps.add(feeDepositState);
                    steps.add(contractEndStep);

                } else {
                    if (secondPaymentStep != null) {
                        secondPaymentStep.setStepStatus(ContractStepStatus.DONE);
                        secondPaymentStep.setActionDate(secondPayment.getPaymentDate());
                        steps.add(secondPaymentStep);
                    }
                    if (thirdPaymentStep != null) {
                        thirdPaymentStep.setStepStatus(ContractStepStatus.DONE);
                        thirdPaymentStep.setActionDate(thirdPayment.getPaymentDate());
                        steps.add(thirdPaymentStep);
                    }

                    if (currentState.equals(ContractState.PAYMENTS_DONE)) {
                        contractStatus.setCurrentActionDueDate(contractDeliveryDate);
                        contractStatus.setAction(Action.DELIVER);
                        contractStatus.setMessages(new String[]{MessageUtil.getMessage("DELIVERY_FOR_SELLER_1"),
                                MessageUtil.getMessage("DELIVERY_FOR_SELLER_2")});
                        contractStatus.setTimerMessage(MessageUtil.getMessage("DELIVERY_FOR_TIMER"));

                        deliveryStep.setStepStatus(ContractStepStatus.CURRENT);
                        steps.add(deliveryStep);

                        steps.add(buyerApprovalStep);
                        steps.add(feeDepositState);
                        steps.add(contractEndStep);

                    } else {
                        if (currentState.equals(ContractState.DELIVERY_IN_PROGRESS)) {
                            contractStatus.setCurrentActionDueDate(contractDeliveryDate);
                            contractStatus.setAction(Action.NONE);
                            contractStatus.setMessages(new String[]{MessageUtil.getMessage("APPROVE_DELIVERY_FOR_SELLER_1",
                                    MessageUtil.getMessage("APPROVE_DELIVERY_FOR_SELLER_2"))});
                            contractStatus.setTimerMessage(MessageUtil.getMessage("APPROVE_DELIVERY_FOR_TIMER"));

                            deliveryStep.setStepStatus(ContractStepStatus.CURRENT);
                            steps.add(deliveryStep);

                            steps.add(buyerApprovalStep);
                            steps.add(feeDepositState);
                            steps.add(contractEndStep);
                        } else {
                            deliveryStep.setStepStatus(ContractStepStatus.DONE);
                            deliveryStep.setActionDate(contract.getDelivery().getDeliveryDate());
                            steps.add(deliveryStep);

                            if (currentState.equals(ContractState.DELIVERY_DONE)) {
                                contractStatus.setCurrentActionDueDate(getOneHourLater(contractDeliveryDate));
                                contractStatus.setAction(Action.NONE);
                                contractStatus.setMessages(new String[]{MessageUtil.getMessage("BUYER_APPROVAL_FOR_SELLER_1"),
                                        MessageUtil.getMessage("BUYER_APPROVAL_FOR_SELLER_2")});
                                contractStatus.setTimerMessage(MessageUtil.getMessage("BUYER_APPROVAL_FOR_TIMER"));

                                buyerApprovalStep.setStepStatus(ContractStepStatus.CURRENT);

                                // TODO: 11/8/20 remove on a later time on

                                contractStatus.setCurrentActionDueDate(null);
                                contractStatus.setAction(Action.NONE);
                                contractStatus.setMessages(new String[]{MessageUtil.getMessage("CONTRACT_END_FOR_SELLER")});
                                contractStatus.setTimerMessage(MessageUtil.getMessage("CONTRACT_END_FOR_TIMER"));

                                feeDepositState.setStepStatus(ContractStepStatus.DONE);
                                contractEndStep.setStepStatus(ContractStepStatus.DONE);


//                                steps.add(buyerApprovalStep);
                                steps.add(feeDepositState);
                                steps.add(contractEndStep);

                            } else {
                                buyerApprovalStep.setStepStatus(ContractStepStatus.DONE);
                                steps.add(buyerApprovalStep);

                                if (currentState.equals(ContractState.BUYER_APPROVAL)) {
                                    contractStatus.setCurrentActionDueDate(getOneDayLater(contractDeliveryDate));
                                    contractStatus.setAction(Action.NONE);
                                    contractStatus.setMessages(new String[]{MessageUtil.getMessage("FEE_DEPOSIT_FOR_SELLER")});
                                    contractStatus.setTimerMessage(MessageUtil.getMessage("FEE_DEPOSIT_FOR_TIMER"));

                                    feeDepositState.setStepStatus(ContractStepStatus.CURRENT);
                                    steps.add(feeDepositState);
                                    steps.add(contractEndStep);

                                } else {
                                    feeDepositState.setStepStatus(ContractStepStatus.DONE);
                                    steps.add(feeDepositState);

                                    //noinspection StatementWithEmptyBody
                                    if (currentState.equals(ContractState.FEE_DEPOSIT_END)) {
                                        contractStatus.setCurrentActionDueDate(null);
                                        contractStatus.setAction(Action.NONE);
                                        contractStatus.setMessages(new String[]{MessageUtil.getMessage("CONTRACT_END_FOR_SELLER")});
                                        contractStatus.setTimerMessage(MessageUtil.getMessage("CONTRACT_END_FOR_TIMER"));

                                        contractEndStep.setStepStatus(ContractStepStatus.CURRENT);
                                        steps.add(contractEndStep);
                                    } else {
                                        // todo : other situations
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        contractStatus.setSteps(steps);
        return contractStatus;
    }

    private ContractStatusModel readContractStatusForBuyer(Contract contract) {

        List<ContractStepModel> steps = new ArrayList<>();

        ContractState currentState = contract.getState();
        Timestamp contractDeliveryDate = contract.getDelivery().getDueDate();
        contract.getPayments().sort(Comparator.comparing(Payment::getId));
        Payment firstPayment = contract.getPayments().get(0);
        Payment secondPayment = null;
        Payment thirdPayment = null;
        ContractStepModel secondPaymentStep = null;
        ContractStepModel thirdPaymentStep = null;
        int paymentsCount = contract.getMultiStepPayment() ? contract.getPayments().size() : 1;
        if (paymentsCount > 1) {
            secondPayment = contract.getPayments().get(1);
            if (paymentsCount > 2) {
                thirdPayment = contract.getPayments().get(2);
            }
        }

        ContractStatusModel contractStatus = new ContractStatusModel();
        contractStatus.setContractId(contract.getId());
        contractStatus.setSellerId(contract.getSellerId());
        contractStatus.setBuyerId(contract.getBuyerId());
        contractStatus.setNow(nowInTimestamp());
        contractStatus.setPaymentsCount(paymentsCount);

        ContractStepModel signStep = new ContractStepModel(
                MessageUtil.getMessage("SIGN_TITLE"),
                ContractState.SIGN_BY_BUYER,
                Actor.BUYER,
                Actor.BUYER,
                Actor.SELLER,
                null,
                getOneHourLater(contract.getSellerSignDate()),
                null,
                ContractStepStatus.WAIT);

        ContractStepModel firstPaymentStep;

        if (contract.getMultiStepPayment()) {
            firstPaymentStep = new ContractStepModel(
                    MessageUtil.getMessage("FIRST_PAYMENT_TITLE_MULTISTEP"),
                    secondPayment == null ? ContractState.PAYMENTS_DONE : ContractState.PAYMENTS_IN_PROGRESS,
                    Actor.BUYER,
                    Actor.BUYER,
                    Actor.VOSOUQ,
                    firstPayment.getAmount(),
                    firstPayment.getDueDate(),
                    firstPayment.getPaymentDate(),
                    ContractStepStatus.WAIT);
        } else {
            firstPaymentStep = new ContractStepModel(
                    MessageUtil.getMessage("FIRST_PAYMENT_TITLE"),
                    secondPayment == null ? ContractState.PAYMENTS_DONE : ContractState.PAYMENTS_IN_PROGRESS,
                    Actor.BUYER,
                    Actor.BUYER,
                    Actor.VOSOUQ,
                    firstPayment.getAmount(),
                    firstPayment.getDueDate(),
                    firstPayment.getPaymentDate(),
                    ContractStepStatus.WAIT);
        }

        if (secondPayment != null) {
            secondPaymentStep = new ContractStepModel(
                    MessageUtil.getMessage("SECOND_PAYMENT_TITLE"),
                    thirdPayment == null ? ContractState.PAYMENTS_DONE : ContractState.PAYMENTS_IN_PROGRESS,
                    Actor.BUYER,
                    Actor.BUYER,
                    Actor.VOSOUQ,
                    secondPayment.getAmount(),
                    secondPayment.getDueDate(),
                    secondPayment.getPaymentDate(),
                    ContractStepStatus.WAIT);
        }

        if (thirdPayment != null) {
            thirdPaymentStep = new ContractStepModel(
                    MessageUtil.getMessage("THIRD_PAYMENT_TITLE"),
                    ContractState.PAYMENTS_DONE,
                    Actor.BUYER,
                    Actor.BUYER,
                    Actor.VOSOUQ,
                    thirdPayment.getAmount(),
                    thirdPayment.getDueDate(),
                    thirdPayment.getPaymentDate(),
                    ContractStepStatus.WAIT);
        }

        ContractStepModel deliveryStep = new ContractStepModel(
                MessageUtil.getMessage("DELIVERY_TITLE_FOR_BUYER"),
                ContractState.DELIVERY_IN_PROGRESS,
                Actor.SELLER,
                Actor.BUYER,
                Actor.SELLER,
                null,
                contract.getDelivery().getDueDate(),
                contract.getDelivery().getDeliveryDate(),
                ContractStepStatus.WAIT);

        ContractStepModel buyerApprovalStep = new ContractStepModel(
                MessageUtil.getMessage("BUYER_APPROVAL_TITLE"),
                ContractState.BUYER_APPROVAL,
                Actor.BUYER,
                Actor.BUYER,
                Actor.VOSOUQ,
                null,
                null,
                contract.getBuyerApproval().getApproveDate(),
                ContractStepStatus.WAIT);

        ContractStepModel feeDepositState = new ContractStepModel(
                MessageUtil.getMessage("FEE_DEPOSIT_TITLE"),
                ContractState.FEE_DEPOSIT_END,
                Actor.VOSOUQ,
                Actor.VOSOUQ,
                Actor.SELLER,
                null,
                null,
                contract.getDelivery().getDeliveryDate(),
                ContractStepStatus.WAIT);

        ContractStepModel contractEndStep = new ContractStepModel(
                MessageUtil.getMessage("CONTRACT_END_TITLE"),
                ContractState.CONTRACT_END,
                Actor.NONE,
                Actor.NONE,
                Actor.NONE,
                null,
                null,
                contract.getDelivery().getDeliveryDate(),
                ContractStepStatus.WAIT);

        if (currentState.equals(ContractState.SIGN_BY_SELLER)) {
            contractStatus.setCurrentActionDueDate(getOneHourLater(contract.getSellerSignDate()));
            contractStatus.setAction(Action.SIGN);
            contractStatus.setMessages(new String[]{MessageUtil.getMessage("SIGN_BY_SELLER_FOR_BUYER_1"),
                    MessageUtil.getMessage("SIGN_BY_SELLER_FOR_BUYER_2")});
            contractStatus.setTimerMessage(MessageUtil.getMessage("SIGN_BY_SELLER_FOR_TIMER"));

            signStep.setStepStatus(ContractStepStatus.CURRENT);
            steps.add(signStep);

            steps.add(firstPaymentStep);
            if (secondPaymentStep != null) steps.add(secondPaymentStep);
            if (thirdPaymentStep != null) steps.add(thirdPaymentStep);
            steps.add(deliveryStep);
            steps.add(buyerApprovalStep);
            steps.add(feeDepositState);
            steps.add(contractEndStep);

        } else {
            signStep.setStepStatus(ContractStepStatus.DONE);
            signStep.setActionDate(contract.getBuyerSignDate());
            steps.add(signStep);

            if (currentState.equals(ContractState.SIGN_BY_BUYER)) {
                contractStatus.setCurrentActionDueDate(firstPayment.getDueDate());
                contractStatus.setAction(Action.PAY);
                contractStatus.setMessages(new String[]{MessageUtil.getMessage("FIRST_PAYMENT_FOR_BUYER_1"),
                        MessageUtil.getMessage("FIRST_PAYMENT_FOR_BUYER_2")});
                contractStatus.setTimerMessage(MessageUtil.getMessage("FIRST_PAYMENT_FOR_TIMER"));

                firstPaymentStep.setStepStatus(ContractStepStatus.CURRENT);
                steps.add(firstPaymentStep);

                if (secondPaymentStep != null) steps.add(secondPaymentStep);
                if (thirdPaymentStep != null) steps.add(thirdPaymentStep);
                steps.add(deliveryStep);
                steps.add(buyerApprovalStep);
                steps.add(feeDepositState);
                steps.add(contractEndStep);

            } else {
                firstPaymentStep.setStepStatus(ContractStepStatus.DONE);
                firstPaymentStep.setActionDate(firstPayment.getPaymentDate());
                steps.add(firstPaymentStep);

                if (currentState.equals(ContractState.PAYMENTS_IN_PROGRESS)) {
                    if (secondPaymentStep != null) {
                        if (secondPayment.getPaymentDate() == null && secondPayment.getStatus().equals(PaymentStatus.UNPAID)) {
                            contractStatus.setCurrentActionDueDate(secondPayment.getDueDate());
                            contractStatus.setAction(Action.PAY);
                            contractStatus.setMessages(new String[]{MessageUtil.getMessage("SECOND_PAYMENT_FOR_BUYER_1"),
                                    MessageUtil.getMessage("SECOND_PAYMENT_FOR_BUYER_2")});
                            contractStatus.setTimerMessage(MessageUtil.getMessage("SECOND_PAYMENT_FOR_TIMER"));

                            secondPaymentStep.setStepStatus(ContractStepStatus.CURRENT);
                            steps.add(secondPaymentStep);
                            if (thirdPaymentStep != null) steps.add(thirdPaymentStep);
                        } else {
                            secondPaymentStep.setStepStatus(ContractStepStatus.DONE);
                            steps.add(secondPaymentStep);
                        }
                    }

                    if (thirdPaymentStep != null) {
                        if (secondPaymentStep != null && !secondPaymentStep.getStepStatus().equals(ContractStepStatus.CURRENT)) {
                            if (thirdPayment.getPaymentDate() == null && thirdPayment.getStatus().equals(PaymentStatus.UNPAID)) {
                                contractStatus.setCurrentActionDueDate(thirdPayment.getDueDate());
                                contractStatus.setAction(Action.PAY);
                                contractStatus.setMessages(new String[]{MessageUtil.getMessage("THIRD_PAYMENT_FOR_BUYER_1"),
                                        MessageUtil.getMessage("THIRD_PAYMENT_FOR_BUYER_2")});
                                contractStatus.setTimerMessage(MessageUtil.getMessage("THIRD_PAYMENT_FOR_TIMER"));

                                thirdPaymentStep.setStepStatus(ContractStepStatus.CURRENT);
                                steps.add(thirdPaymentStep);
                            } else {
                                thirdPaymentStep.setStepStatus(ContractStepStatus.DONE);
                                steps.add(thirdPaymentStep);
                            }
                        }
                    }

                    steps.add(deliveryStep);
                    steps.add(buyerApprovalStep);
                    steps.add(feeDepositState);
                    steps.add(contractEndStep);

                } else {
                    if (secondPaymentStep != null) {
                        secondPaymentStep.setStepStatus(ContractStepStatus.DONE);
                        secondPaymentStep.setActionDate(secondPayment.getPaymentDate());
                        steps.add(secondPaymentStep);
                    }
                    if (thirdPaymentStep != null) {
                        thirdPaymentStep.setStepStatus(ContractStepStatus.DONE);
                        thirdPaymentStep.setActionDate(thirdPayment.getPaymentDate());
                        steps.add(thirdPaymentStep);
                    }

                    if (currentState.equals(ContractState.PAYMENTS_DONE)) {
                        contractStatus.setCurrentActionDueDate(contractDeliveryDate);
                        contractStatus.setAction(Action.SCAN);
                        contractStatus.setMessages(new String[]{MessageUtil.getMessage("DELIVERY_FOR_BUYER_1"),
                                MessageUtil.getMessage("DELIVERY_FOR_BUYER_2")});
                        contractStatus.setTimerMessage(MessageUtil.getMessage("DELIVERY_FOR_TIMER"));

                        deliveryStep.setStepStatus(ContractStepStatus.CURRENT);
                        steps.add(deliveryStep);

                        steps.add(buyerApprovalStep);
                        steps.add(feeDepositState);
                        steps.add(contractEndStep);

                    } else {
                        if (currentState.equals(ContractState.DELIVERY_IN_PROGRESS)) {
                            contractStatus.setCurrentActionDueDate(getOneHourLater(contractDeliveryDate));
                            contractStatus.setAction(Action.DELIVERY_APPROVAL);
                            contractStatus.setMessages(new String[]{MessageUtil.getMessage("APPROVE_DELIVERY_FOR_BUYER_1"),
                                    MessageUtil.getMessage("APPROVE_DELIVERY_FOR_BUYER_2")});
                            contractStatus.setTimerMessage(MessageUtil.getMessage("APPROVE_DELIVERY_FOR_TIMER"));

                            deliveryStep.setStepStatus(ContractStepStatus.CURRENT);
                            steps.add(deliveryStep);

                            steps.add(buyerApprovalStep);
                            steps.add(feeDepositState);
                            steps.add(contractEndStep);

                        } else {
                            deliveryStep.setStepStatus(ContractStepStatus.DONE);
                            deliveryStep.setActionDate(contract.getDelivery().getDeliveryDate());
                            steps.add(deliveryStep);

                            if (currentState.equals(ContractState.DELIVERY_DONE)) {
                                contractStatus.setCurrentActionDueDate(getOneHourLater(contractDeliveryDate));
                                contractStatus.setAction(Action.CONTRACT_APPROVAL);
                                contractStatus.setMessages(new String[]{MessageUtil.getMessage("BUYER_APPROVAL_FOR_BUYER_1"),
                                        MessageUtil.getMessage("BUYER_APPROVAL_FOR_BUYER_2")});
                                contractStatus.setTimerMessage(MessageUtil.getMessage("BUYER_APPROVAL_FOR_TIMER"));

                                buyerApprovalStep.setStepStatus(ContractStepStatus.CURRENT);
//                                steps.add(deliveryStep);


                                // TODO: 11/8/20 remove later
                                contractStatus.setCurrentActionDueDate(null);
                                contractStatus.setAction(Action.NONE);
                                contractStatus.setMessages(new String[]{MessageUtil.getMessage("CONTRACT_END_FOR_BUYER")});
                                contractStatus.setTimerMessage(MessageUtil.getMessage("CONTRACT_END_FOR_TIMER"));

                                feeDepositState.setStepStatus(ContractStepStatus.DONE);

                                contractEndStep.setStepStatus(ContractStepStatus.DONE);


//                                steps.add(buyerApprovalStep);
                                steps.add(feeDepositState);
                                steps.add(contractEndStep);
                            } else {
                                buyerApprovalStep.setStepStatus(ContractStepStatus.DONE);
                                steps.add(buyerApprovalStep);

                                if (currentState.equals(ContractState.BUYER_APPROVAL)) {
                                    contractStatus.setCurrentActionDueDate(getOneDayLater(contractDeliveryDate));
                                    contractStatus.setAction(Action.DEPOSIT);
                                    contractStatus.setMessages(new String[]{MessageUtil.getMessage("FEE_DEPOSIT_FOR_BUYER")});
                                    contractStatus.setTimerMessage(MessageUtil.getMessage("FEE_DEPOSIT_FOR_TIMER"));

                                    feeDepositState.setStepStatus(ContractStepStatus.CURRENT);
                                    steps.add(feeDepositState);
                                    steps.add(contractEndStep);

                                } else {
                                    feeDepositState.setStepStatus(ContractStepStatus.DONE);
                                    steps.add(feeDepositState);

                                    //noinspection StatementWithEmptyBody
                                    if (currentState.equals(ContractState.FEE_DEPOSIT_END)) {
                                        contractStatus.setCurrentActionDueDate(null);
                                        contractStatus.setAction(Action.NONE);
                                        contractStatus.setMessages(new String[]{MessageUtil.getMessage("CONTRACT_END_FOR_BUYER")});
                                        contractStatus.setTimerMessage(MessageUtil.getMessage("CONTRACT_END_FOR_TIMER"));

                                        contractEndStep.setStepStatus(ContractStepStatus.CURRENT);
                                        steps.add(contractEndStep);
                                    } else {
                                        // todo : other situations
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        contractStatus.setSteps(steps);
        return contractStatus;
    }

    private void setCommonContractFields(Contract contract,
                                         ContractTemplate contractTemplate,
                                         Suggestion confirmedSuggestion,
                                         Long sellerId,
                                         String title,
                                         Long price,
                                         Long terminationPenalty,
                                         String province,
                                         String city,
                                         String neighbourhood,
                                         String description,
                                         List<Long> otherAttachments,
                                         String signDescription) {

        contract.setCreateDate(nowInTimestamp());
        contract.setUpdateDate(nowInTimestamp());

        contract.setContractTemplate(contractTemplate);
        contract.setSuggestion(confirmedSuggestion);

        contract.setBuyerId(confirmedSuggestion.getBuyerId());
        contract.setSellerSignDate(nowInTimestamp());
        contract.setState(ContractState.SIGN_BY_SELLER);

        contract.setSellerId(sellerId);
        contract.setTitle(title);
        contract.setPrice(price);
        contract.setTerminationPenalty(terminationPenalty);
        contract.setProvince(province);
        contract.setCity(city);
        contract.setNeighbourhood(neighbourhood);
        contract.setDescription(description);
        contract.setSignDescription(signDescription);

        if (!CollectionUtils.isEmpty(otherAttachments)) {
            contract.setOtherAttachments(
                    otherAttachments.stream()
                            .map(attachment -> fileAddressService.findAndValidate(attachment, FileType.ATTACHMENT_FILE, sellerId))
                            .collect(Collectors.toList()));
        }
    }

    private void setContractRelatedEntities(Contract contract,
                                            Timestamp deliveryDate,
                                            List<PaymentTemplateModel> paymentTemplates,
                                            Long sellerId,
                                            List<Long> fileIds) {

        paymentService.saveContractPayments(contract, paymentTemplates);

        contract.setDelivery(contractDeliveryService.save(contract, deliveryDate));

        contract.setBuyerApproval(contractBuyerApprovalService.save(contract, getOneHourLater(deliveryDate)));

        if (!CollectionUtils.isEmpty(fileIds)) {
            contract.setFiles(
                    fileIds.stream()
                            .map(attachment -> fileAddressService.findAndValidate(attachment, FileType.PRODUCT_IMAGE, sellerId))
                            .collect(Collectors.toList()));
        }

    }

    private CommodityContract buildCommodity(Long pricePerUnit,
                                             Integer quantity,
                                             Unit unit) {

        CommodityContract commodityContract = new CommodityContract();
        commodityContract.setPricePerUnit(pricePerUnit);
        commodityContract.setQuantity(quantity);
        commodityContract.setUnit(unit);

        return commodityContract;
    }

    private VehicleContract buildVehicle(Integer manufactureYear,
                                         Integer usage,
                                         Gearbox gearbox,
                                         Fuel fuel,
                                         BodyStatus bodyStatus,
                                         Color color,
                                         Boolean isOwner) {

        VehicleContract contract = new VehicleContract();
        contract.setManufactureYear(manufactureYear);
        contract.setUsage(usage);
        contract.setGearbox(gearbox);
        contract.setFuel(fuel);
        contract.setBodyStatus(bodyStatus);
        contract.setColor(color);
        contract.setIsOwner(isOwner);

        return contract;
    }

    private Contract save(Contract contract) {
        return contractRepository.save(contract);
    }

    private void sendOnKafka(Contract contract, ContractProgressStatus dueStatus, ObligatedParty obligatedParty) {
        sendOnKafka(contract, dueStatus, obligatedParty, false);
    }

    private void sendOnKafka(Contract contract, ContractProgressStatus dueStatus, ObligatedParty obligatedParty, boolean loadPayments) {
        ContractPayload contractPayload = new ContractPayload();
        contractPayload.setSellerId(contract.getSellerId());
        contractPayload.setBuyerId(contract.getBuyerId());
        contractPayload.setContractId(contract.getId());
        contractPayload.setAmount(contract.getPrice());
        contractPayload.setStartDate(contract.getCreateDate());
        contractPayload.setDueStatus(dueStatus);
        contractPayload.setObligatedParty(obligatedParty);
        if (loadPayments) {
            contractPayload.setPayments(contract.getPayments().stream()
                    .map(PaymentMapper::convertToPaymentPayload).collect(Collectors.toList()));
        }
        contractsKafkaProducer.send(contractPayload);
    }

    private void sendOnKafka(Payment payment) {
        contractPaymentsKafkaProducer.send(convertToPaymentPayload(payment));
    }


    @SuppressWarnings("SameParameterValue")
    private void sendPushAndMessage(Long toUserId, Long subjectId, String subjectType, String messageCode, String arg) {
//        messageService.send(
//                toUserId,
//                MessageUtil.getMessage(messageCode, arg),
//                subjectId,
//                subjectType);

        notificationService.send(
                toUserId,
                MessageUtil.getMessage(messageCode, arg),
                subjectId,
                subjectType);
    }
}

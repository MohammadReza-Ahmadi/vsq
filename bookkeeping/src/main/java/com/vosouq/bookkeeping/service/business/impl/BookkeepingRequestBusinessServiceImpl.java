package com.vosouq.bookkeeping.service.business.impl;

import com.vosouq.bookkeeping.controller.dto.requests.GetIpgPaymentRefIdRequest;
import com.vosouq.bookkeeping.controller.dto.requests.PayRequest;
import com.vosouq.bookkeeping.controller.dto.responses.ContractGetAmountResponse;
import com.vosouq.bookkeeping.controller.dto.responses.IpgResponse;
import com.vosouq.bookkeeping.controller.dto.responses.PayResponse;
import com.vosouq.bookkeeping.controller.mapper.BookkeepingRequestMapper;
import com.vosouq.bookkeeping.enumeration.GatewayType;
import com.vosouq.bookkeeping.enumeration.RequestStatus;
import com.vosouq.bookkeeping.enumeration.RequestType;
import com.vosouq.bookkeeping.enumeration.RequesterType;
import com.vosouq.bookkeeping.exception.VosouqEntityNotFoundException;
import com.vosouq.bookkeeping.exception.thirdparty.ContractCallException;
import com.vosouq.bookkeeping.exception.thirdparty.PaymentGatewayCallException;
import com.vosouq.bookkeeping.model.BookkeepingRequest;
import com.vosouq.bookkeeping.model.domainobject.VoucherFormulaAmount;
import com.vosouq.bookkeeping.model.journalizing.Account;
import com.vosouq.bookkeeping.model.journalizing.Commission;
import com.vosouq.bookkeeping.model.journalizing.ValueAddedTax;
import com.vosouq.bookkeeping.model.posting.PostingDebitCreditSubsidiaryLedgers;
import com.vosouq.bookkeeping.service.business.*;
import com.vosouq.bookkeeping.service.business.thirdparty.ContractRepository;
import com.vosouq.bookkeeping.service.business.thirdparty.PaymentGatewayRepository;
import com.vosouq.bookkeeping.service.crud.AccountCrudService;
import com.vosouq.bookkeeping.service.crud.BookkeepingRequestCrudService;
import com.vosouq.bookkeeping.util.AppUtil;
import com.vosouq.commons.config.feign.FeignDecodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class BookkeepingRequestBusinessServiceImpl implements BookkeepingRequestBusinessService {
    private final BookkeepingRequestCrudService bookkeepingRequestCrudService;
    private final ContractRepository contractRepository;
    private final PostingModelBusinessService postingModelBusinessService;
    private final UserBusinessService userBusinessService;
    private final AccountBusinessService accountBusinessService;
    private final BookkeepingBusinessService bookkeepingBusinessService;
    private final PaymentGatewayRepository paymentGatewayRepository;
    private final AccountCrudService accountCrudService;


    public BookkeepingRequestBusinessServiceImpl(BookkeepingRequestCrudService bookkeepingRequestCrudService, ContractRepository contractRepository, PostingModelBusinessService postingModelBusinessService, UserBusinessService userBusinessService, AccountBusinessService accountBusinessService, BookkeepingBusinessService bookkeepingBusinessService, PaymentGatewayRepository paymentGatewayRepository, AccountCrudService accountCrudService) {
        this.bookkeepingRequestCrudService = bookkeepingRequestCrudService;
        this.contractRepository = contractRepository;
        this.postingModelBusinessService = postingModelBusinessService;
        this.userBusinessService = userBusinessService;
        this.accountBusinessService = accountBusinessService;
        this.bookkeepingBusinessService = bookkeepingBusinessService;
        this.paymentGatewayRepository = paymentGatewayRepository;
        this.accountCrudService = accountCrudService;
    }


    /**
     * @param amount
     * @return
     */
    @Override
    public BookkeepingRequest createAccountRechargeRequest(BigDecimal amount) {
        Long userId = userBusinessService.getCurrentUserId();

        /* call payment-gateway and get new RefId for Ipg payment*/
        IpgResponse ipgRefIdResponse;
        try {
            ipgRefIdResponse = paymentGatewayRepository.getIpgPaymentRequestRefId(new GetIpgPaymentRefIdRequest(userId, amount));
        } catch (Exception e) {
            e.printStackTrace();
            throw new PaymentGatewayCallException(((FeignDecodeException) e).getErrorMessage().getMessage());
        }

//        Account creditAccount = accountBusinessService.getOrCreateOnlineUserAccount();

        PostingDebitCreditSubsidiaryLedgers debitAndCreditSubsidiaryLedgers = postingModelBusinessService.getDebitAndCreditSubsidiaryLedgers(RequestType.ACCOUNT_RECHARGE);
        BookkeepingRequest bookkeepingRequest = BookkeepingRequestMapper.map(RequesterType.ACCOUNT, userId, debitAndCreditSubsidiaryLedgers, null, null, amount);
        bookkeepingRequest.setRequesterId(userId);
        bookkeepingRequest.setGatewayRefId(ipgRefIdResponse.getRefId());
        bookkeepingRequest.setGatewayOrderId(ipgRefIdResponse.getOrderId());
        bookkeepingRequest.setCommissionAmount(BigDecimal.ZERO);
        bookkeepingRequest.setVatAmount(BigDecimal.ZERO);
        bookkeepingRequest.setRequestStatus(RequestStatus.PENDING);
        bookkeepingRequest.setDescription("account recharge by user id:" + userId + " and amount:" + amount);

        bookkeepingRequestCrudService.createOrUpdate(bookkeepingRequest);
        Account creditAccount = accountBusinessService.getOrCreateOnlineUserAccount(bookkeepingRequest.getId());
        bookkeepingRequest.setCreditAccount(creditAccount);
        bookkeepingRequestCrudService.createOrUpdate(bookkeepingRequest);
        log.debug("Bookkeeping request of charge account with creditAccountId:[{}] and amount:[{}] is created.", creditAccount.getId(), amount);
        return bookkeepingRequest;
    }

    @Override
    public BookkeepingRequest createAccountWithdrawalRequest(String iban, BigDecimal amount) {
        /*create bookkeepingRequest */
        Long userId = userBusinessService.getCurrentUserId();
        Account debitAccount = accountBusinessService.getOnlineUserAccount();
        PostingDebitCreditSubsidiaryLedgers debitAndCreditSubsidiaryLedgers = postingModelBusinessService.getDebitAndCreditSubsidiaryLedgers(RequestType.ACCOUNT_WITHDRAWAL);
        BookkeepingRequest bookkeepingRequest = BookkeepingRequestMapper.map(RequesterType.ACCOUNT, userId, debitAndCreditSubsidiaryLedgers, debitAccount, null, amount);
        bookkeepingRequest.setRequesterId(userId);
        bookkeepingRequest.setRequestStatus(RequestStatus.SUCCESS);
        bookkeepingRequest.setDescription("account withdrawal by user id:" + userId + " and amount:" + amount);

        bookkeepingRequestCrudService.createOrUpdate(bookkeepingRequest);
        log.debug("Bookkeeping request of account withdrawal with debitAccountId:[{}] and amount:[{}] is created.", debitAccount.getId(), amount);
        return bookkeepingRequest;
    }

    @Override
    public BookkeepingRequest createContractRegistrationRequest(Long contractId) {
        Long userId = userBusinessService.getCurrentUserId();
        PostingDebitCreditSubsidiaryLedgers debitAndCreditSubsidiaryLedgers = postingModelBusinessService.getDebitAndCreditSubsidiaryLedgers(RequestType.CONTRACT_CREATE);
        BookkeepingRequest bookkeepingRequest = BookkeepingRequestMapper.map(RequesterType.CONTRACT, userId, debitAndCreditSubsidiaryLedgers, BigDecimal.ONE);
        bookkeepingRequest.setRequesterId(contractId);
        bookkeepingRequest.setRequestStatus(RequestStatus.SUCCESS);
        bookkeepingRequest.setDescription("creat contract by id:" + contractId);
        bookkeepingRequestCrudService.createOrUpdate(bookkeepingRequest);
        log.debug("Bookkeeping request of create contract with contract id:[{}] is created.", contractId);
        return bookkeepingRequest;
    }

    @Override
    public BookkeepingRequest createContractIpgPaymentRequest(Long contractId) {
        /* call contract microservice to validate payment request on this contract and get back amount value for payment */
        ContractGetAmountResponse amountResponse;
        try {
            amountResponse = contractRepository.getContractPaymentAmount(contractId);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ContractCallException(((FeignDecodeException) e).getErrorMessage().getMessage());
        }

        BigDecimal amount = BigDecimal.valueOf(amountResponse.getAmount());

        /* calculate commission amount based on rate from commission table */
        BigDecimal commissionAmount = new Commission().getCommissionAmount(amount);
        /* calculate vat amount based on rate from valueAddedTax table */
        BigDecimal vatAmount = new ValueAddedTax().getVATAmount(commissionAmount);
        /* calculate totalAmount by adding commission and vat amounts for sending to ipg  */
        BigDecimal totalAmount = amount.add(commissionAmount).add(vatAmount);

        /* call payment-gateway and get new RefId for Ipg payment*/
        IpgResponse ipgRefIdResponse;
        try {
            ipgRefIdResponse = paymentGatewayRepository.getIpgPaymentRequestRefId(new GetIpgPaymentRefIdRequest(contractId, totalAmount));
        } catch (Exception e) {
            e.printStackTrace();
            throw new PaymentGatewayCallException(((FeignDecodeException) e).getErrorMessage().getMessage());
        }

        Long userId = userBusinessService.getCurrentUserId();

        /* create required data for posting order to vouchers creation */
        PostingDebitCreditSubsidiaryLedgers debitAndCreditSubsidiaryLedgers = postingModelBusinessService.getDebitAndCreditSubsidiaryLedgers(RequestType.CONTRACT_PAYMENT);

        /* create bookkeepingRequest instance */
        BookkeepingRequest bookkeepingRequest = BookkeepingRequestMapper.map(RequesterType.CONTRACT, userId, debitAndCreditSubsidiaryLedgers, totalAmount);
        bookkeepingRequest.setRequesterId(contractId);
        bookkeepingRequest.setGatewayRefId(ipgRefIdResponse.getRefId());
        bookkeepingRequest.setGatewayOrderId(ipgRefIdResponse.getOrderId());
        bookkeepingRequest.setCommissionAmount(commissionAmount);
        bookkeepingRequest.setVatAmount(vatAmount);
        bookkeepingRequest.setRequestStatus(RequestStatus.PENDING);
        bookkeepingRequest.setDescription("contract ipg payment request by contract id:" + contractId + " and gateWayRefId:" + ipgRefIdResponse.getRefId() + " and amount:" + totalAmount);
        bookkeepingRequestCrudService.createOrUpdate(bookkeepingRequest);
        Account debitAccount = accountBusinessService.getOrCreateOnlineUserAccount(bookkeepingRequest.getId());
        bookkeepingRequest.setDebitAccount(debitAccount);
        bookkeepingRequestCrudService.createOrUpdate(bookkeepingRequest);
        log.debug("Bookkeeping request contract ipg payment with contractId:[{}] and amount:[{}] is created.", contractId, amount);
        return bookkeepingRequest;
    }

    @Override
    public BookkeepingRequest createContractAccountPaymentRequest(Long contractId) {
        /* call contract microservice to validate payment request on this contract and get back amount value for payment */
        ContractGetAmountResponse amountResponse;
        try {
            amountResponse = contractRepository.getContractPaymentAmount(contractId);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ContractCallException(((FeignDecodeException) e).getErrorMessage().getMessage());
        }

        BigDecimal amount = BigDecimal.valueOf(amountResponse.getAmount());

        /* calculate commission amount based on rate from commission table */
        BigDecimal commissionAmount = new Commission().getCommissionAmount(amount);
        /* calculate vat amount based on rate from valueAddedTax table */
        BigDecimal vatAmount = new ValueAddedTax().getVATAmount(commissionAmount);
        /* calculate totalAmount by adding commission and vat amounts for sending to ipg  */
        BigDecimal totalAmount = amount.add(commissionAmount).add(vatAmount);

        Long userId = userBusinessService.getCurrentUserId();

        /* create required data for posting order to vouchers creation */
        PostingDebitCreditSubsidiaryLedgers debitAndCreditSubsidiaryLedgers = postingModelBusinessService.getDebitAndCreditSubsidiaryLedgers(RequestType.CONTRACT_PAYMENT);

        /* create bookkeepingRequest instance */
        BookkeepingRequest bookkeepingRequest = BookkeepingRequestMapper.map(RequesterType.CONTRACT, userId, debitAndCreditSubsidiaryLedgers, totalAmount);
        bookkeepingRequest.setRequesterId(contractId);
        bookkeepingRequest.setCommissionAmount(commissionAmount);
        bookkeepingRequest.setVatAmount(vatAmount);
        bookkeepingRequest.setRequestStatus(RequestStatus.SUCCESS);
        bookkeepingRequest.setDescription("contract account payment request by contract id:" + contractId + " and amount:" + totalAmount);
        bookkeepingRequestCrudService.createOrUpdate(bookkeepingRequest);
        log.debug("Bookkeeping request contract account payment with contractId:[{}] and amount:[{}] is created.", contractId, amount);

        Account debitAccount = accountBusinessService.getOrCreateOnlineUserAccount(bookkeepingRequest.getId());
        bookkeepingRequest.setDebitAccount(debitAccount);
        bookkeepingRequestCrudService.createOrUpdate(bookkeepingRequest);
        successContractAccountPayment(bookkeepingRequest);
        return bookkeepingRequest;
    }

    @Override
    public void createSuccessIpgPaymentRequest(Long gatewayOrderId) {
        /* load BookkeepingRequest by gatewayOrderId from db */
        BookkeepingRequest bookkeepingRequest = bookkeepingRequestCrudService.findByGatewayOrderId(gatewayOrderId);
        if (AppUtil.isNull(bookkeepingRequest)) {
            throw new VosouqEntityNotFoundException(String.format("There is no any BookkeepingRequest record by gatewayOrderId:[%s]", gatewayOrderId));
        }

        /* AccountRecharge when success ipg response is ACCOUNT_CHARGE */
        if (bookkeepingRequest.getRequestType().isAccountRecharge()) {
            successIpgInAccountRecharge(bookkeepingRequest);
        }

        /* ContractPayment when success ipg response is CONTRACT_PAYMENT */
        if (bookkeepingRequest.getRequestType().isContractPayment()) {
            successIpgInContractPayment(bookkeepingRequest);
        }
    }

    private void successIpgInAccountRecharge(BookkeepingRequest bookkeepingRequest) {
        /* deposit recharge amount without commission and vat amount withdrawable balance of customer account */
        accountBusinessService.depositToAccountWithdrawableBalance(bookkeepingRequest.getCreditAccount(), bookkeepingRequest.getAmount());

        /* create voucher */
        bookkeepingBusinessService.createVouchers(bookkeepingRequest, RequestType.ACCOUNT_RECHARGE);

        /* update status of bookkeepingRequest to payment-success in db */
        bookkeepingRequest.setRequestStatus(RequestStatus.SUCCESS);
        bookkeepingRequestCrudService.createOrUpdate(bookkeepingRequest);
    }

    private void successIpgInContractPayment(BookkeepingRequest bookkeepingRequest) {
        /* call contract microservice to inform by contract paid amount*/
        try {
            contractRepository.informContractBy‌PaidAmount(bookkeepingRequest.getRequesterId(), bookkeepingRequest.getAmountWithoutCommissionAndVat().longValue());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ContractCallException(((FeignDecodeException) e).getErrorMessage().getMessage());
        }

        /* create account recharge voucher */
        VoucherFormulaAmount rechargeVoucherFormulaAmount = bookkeepingBusinessService.createVouchers(bookkeepingRequest, RequestType.ACCOUNT_RECHARGE);
        /* get buyer's account */
        Account debitAccount = bookkeepingRequest.getDebitAccount();
        /* deposit contract amount + commission amount + vat amount to withdrawable balance of buyer's account */
        accountBusinessService.depositToAccountWithdrawableBalance(debitAccount, rechargeVoucherFormulaAmount.getAmount());

        /* create contract payment voucher */
        VoucherFormulaAmount paymentVoucherFormulaAmount = bookkeepingBusinessService.createVouchers(bookkeepingRequest, RequestType.CONTRACT_PAYMENT);
        /* withdraw commission amount from withdrawable balance of buyer's account */
        accountBusinessService.withdrawFromAccountWithdrawableBalance(debitAccount, paymentVoucherFormulaAmount.getCommissionAmount(), bookkeepingRequest.getId());
        /* withdraw vat amount from withdrawable balance of buyer's account */
        accountBusinessService.withdrawFromAccountWithdrawableBalance(debitAccount, paymentVoucherFormulaAmount.getVATAmount(), bookkeepingRequest.getId());
        /* withdraw contract amount from withdrawable balance of buyer's account */
        accountBusinessService.withdrawFromAccountWithdrawableBalance(debitAccount, paymentVoucherFormulaAmount.getRemainAmount(), bookkeepingRequest.getId());
        /* block contract amount from withdrawable balance of buyer's account and deposit to its blocked amount */
        accountBusinessService.blockAccountWithdrawableBalance(debitAccount, paymentVoucherFormulaAmount.getRemainAmount());

        /* update status of bookkeepingRequest to payment-success in db */
        bookkeepingRequest.setRequestStatus(RequestStatus.SUCCESS);
        bookkeepingRequestCrudService.createOrUpdate(bookkeepingRequest);
    }

    private void successContractAccountPayment(BookkeepingRequest bookkeepingRequest) {
        /* call contract microservice to inform by contract paid amount*/
        try {
            contractRepository.informContractBy‌PaidAmount(bookkeepingRequest.getRequesterId(), bookkeepingRequest.getAmountWithoutCommissionAndVat().longValue());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ContractCallException(((FeignDecodeException) e).getErrorMessage().getMessage());
        }

        /* create contract payment voucher */
        VoucherFormulaAmount paymentVoucherFormulaAmount = bookkeepingBusinessService.createVouchers(bookkeepingRequest, RequestType.CONTRACT_PAYMENT);
        /* get buyer's account */
        Account debitAccount = bookkeepingRequest.getDebitAccount();
        /* withdraw commission amount from withdrawable balance of buyer's account */
        accountBusinessService.withdrawFromAccountWithdrawableBalance(debitAccount, paymentVoucherFormulaAmount.getCommissionAmount(), bookkeepingRequest.getId());
        /* withdraw vat amount from withdrawable balance of buyer's account */
        accountBusinessService.withdrawFromAccountWithdrawableBalance(debitAccount, paymentVoucherFormulaAmount.getVATAmount(), bookkeepingRequest.getId());
        /* withdraw contract amount from withdrawable balance of buyer's account */
        accountBusinessService.withdrawFromAccountWithdrawableBalance(debitAccount, paymentVoucherFormulaAmount.getRemainAmount(), bookkeepingRequest.getId());
        /* block contract amount from withdrawable balance of buyer's account and deposit to its blocked amount */
        accountBusinessService.blockAccountWithdrawableBalance(debitAccount, paymentVoucherFormulaAmount.getRemainAmount());
    }

    @Override
    public void createFailedIpgPaymentRequest(Long gatewayOrderId) {
        BookkeepingRequest bookkeepingRequest = bookkeepingRequestCrudService.findByGatewayOrderId(gatewayOrderId);
        bookkeepingRequest.setRequestStatus(RequestStatus.FAILED);
    }

    @Override
    public BookkeepingRequest createContractGoodsDeliveryRequest(Long contractId) {
        Long userId = userBusinessService.getCurrentUserId();
        PostingDebitCreditSubsidiaryLedgers debitAndCreditSubsidiaryLedgers = postingModelBusinessService.getDebitAndCreditSubsidiaryLedgers(RequestType.CONTRACT_GOODS_DELIVERY);
        BookkeepingRequest bookkeepingRequest = BookkeepingRequestMapper.map(RequesterType.CONTRACT, userId, debitAndCreditSubsidiaryLedgers, BigDecimal.ONE);
        bookkeepingRequest.setRequesterId(contractId);
        bookkeepingRequest.setRequestStatus(RequestStatus.SUCCESS);
        bookkeepingRequest.setDescription("create goods delivery request on contractId:" + contractId);
        bookkeepingRequestCrudService.createOrUpdate(bookkeepingRequest);
        log.debug("Bookkeeping request of contract's goods delivery with contract id:[{}] is created.", contractId);
        return bookkeepingRequest;
    }

    @Override
    public BookkeepingRequest createSettlementContractRequest(Long contractId, Long sellerUserId) {
        Long userId = userBusinessService.getCurrentUserId();

        /* load contractPaymentRequestList */
        List<BookkeepingRequest> contractPaymentRequestList = bookkeepingRequestCrudService.getAllByRequesterIdAndPostingModelCode(contractId, RequestType.CONTRACT_PAYMENT, RequestStatus.SUCCESS);

        /* create settlement bookkeepingRequest */
        PostingDebitCreditSubsidiaryLedgers debitAndCreditSubsidiaryLedgers = postingModelBusinessService.getDebitAndCreditSubsidiaryLedgers(RequestType.CONTRACT_SETTLEMENT);
        BookkeepingRequest settlementBookkeepingRequest = BookkeepingRequestMapper.map(RequesterType.CONTRACT, userId, debitAndCreditSubsidiaryLedgers, BigDecimal.ZERO);
        settlementBookkeepingRequest.setRequesterId(contractId);
        settlementBookkeepingRequest.setCommissionAmount(BigDecimal.ZERO);
        settlementBookkeepingRequest.setVatAmount(BigDecimal.ZERO);

        /* loop to add all contractPaymentRequest's commission and vat amounts and set to settlementBookkeepingRequest */
        for (BookkeepingRequest contractPaymentRequest : contractPaymentRequestList) {
            /*calculate each payment's settlementAmount by adding commission and vat amounts */
            BigDecimal settlementAmount = contractPaymentRequest.getAmount().subtract(contractPaymentRequest.getCommissionAmount().add(contractPaymentRequest.getVatAmount()));

            /* add each calculated settlement amount to bookkeeping request*/
            settlementBookkeepingRequest.setAmount(settlementBookkeepingRequest.getAmount().add(settlementAmount));
            settlementBookkeepingRequest.setCommissionAmount(settlementBookkeepingRequest.getCommissionAmount().add(contractPaymentRequest.getCommissionAmount()));
            settlementBookkeepingRequest.setVatAmount(settlementBookkeepingRequest.getVatAmount().add(contractPaymentRequest.getVatAmount()));
        }

        log.debug("Bookkeeping request of settlement contract with contact id:[{}] and amount:[{}] is created.", contractId, settlementBookkeepingRequest.getAmount());
        settlementBookkeepingRequest.setDescription("settlement contract by id:" + contractId + " and amount:" + settlementBookkeepingRequest.getAmount());
        settlementBookkeepingRequest.setRequestStatus(RequestStatus.SUCCESS);
        bookkeepingRequestCrudService.createOrUpdate(settlementBookkeepingRequest);

        Account debitAccount = accountBusinessService.getOrCreateOnlineUserAccount(sellerUserId, settlementBookkeepingRequest.getId());
        Account creditAccount = accountCrudService.findByUserId(userId);
        settlementBookkeepingRequest.setDebitAccount(debitAccount);
        settlementBookkeepingRequest.setCreditAccount(creditAccount);

        return settlementBookkeepingRequest;
    }

    /**
     * This method save PaymentRequest info and return generated id
     * @param payRequest
     */
    @Override
    public PayResponse createBookkeepingRequest_DEMO(PayRequest payRequest, GatewayType gatewayType) {
        /** map payRequest properties to PaymentRequest entity*/
        BookkeepingRequest bookkeepingRequest = BookkeepingRequestMapper.map(payRequest, gatewayType);

        /** load account of recipient user by recipientId
         *  actually this is Credit account in bookkeeping */
        Account recipientAccount = accountCrudService.findByUserId(payRequest.getRecipientId());
        bookkeepingRequest.setCreditAccount(recipientAccount);

        /** load account of payer user by payerId
         * actually this is Debit account in bookkeeping */
        Account payerAccount = accountCrudService.findByUserId(payRequest.getPayerId());
        bookkeepingRequest.setDebitAccount(payerAccount);
        Long userId = 1023L; //todo this is a sample userId, later this id must be fetched from UserProfile data
        bookkeepingRequest.setUserId(userId);

        GetIpgPaymentRefIdRequest getIpgPaymentRefIdRequest = BookkeepingRequestMapper.mapToGatewayPayRequest(bookkeepingRequest);
        getIpgPaymentRefIdRequest.setMobileNo(payRequest.getMobileNo());
//        String gatewayRefId = "AF82041a2Bf6989c7fF9";

        IpgResponse response = paymentGatewayRepository.getIpgPaymentRequestRefId(getIpgPaymentRefIdRequest);
        bookkeepingRequest.setGatewayRefId(response.getRefId());
        bookkeepingRequestCrudService.createOrUpdate(bookkeepingRequest);

        /** generate payResponse object*/
        PayResponse payResponse = PayResponse.builder()
                .paymentRequestId(bookkeepingRequest.getId())
                .gatewayRefId(response.getRefId())
                .mobileNo(payRequest.getMobileNo())
                .build();

        return payResponse;
    }
}

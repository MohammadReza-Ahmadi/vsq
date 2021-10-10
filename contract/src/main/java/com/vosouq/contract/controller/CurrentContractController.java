package com.vosouq.contract.controller;

import com.vosouq.commons.annotation.Created;
import com.vosouq.commons.annotation.NoContent;
import com.vosouq.commons.annotation.VosouqRestController;
import com.vosouq.commons.model.BinaryDataHolder;
import com.vosouq.commons.model.OnlineUser;
import com.vosouq.contract.controller.dto.*;
import com.vosouq.contract.controller.mapper.ContractMapper;
import com.vosouq.contract.controller.mapper.FileAddressMapper;
import com.vosouq.contract.controller.mapper.PaymentMapper;
import com.vosouq.contract.exception.FileNotFoundException;
import com.vosouq.contract.exception.QrCodeNotFoundException;
import com.vosouq.contract.model.*;
import com.vosouq.contract.service.ContractService;
import com.vosouq.contract.service.MessageService;
import com.vosouq.contract.service.feign.ProfileServiceClient;
import com.vosouq.contract.utills.QrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.stream.Collectors;

import static com.vosouq.contract.utills.TimeUtil.fromMillis;
import static com.vosouq.contract.utills.TimeUtil.nowInMillis;

@VosouqRestController
@RequestMapping("/currents")
public class CurrentContractController {

    private final ContractService contractService;
    private final OnlineUser onlineUser;
    private final ProfileServiceClient profileServiceClient;

    @Autowired
    public CurrentContractController(ContractService contractService,
                                     @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
                                             OnlineUser onlineUser,
                                     ProfileServiceClient profileServiceClient) {

        this.contractService = contractService;
        this.onlineUser = onlineUser;
        this.profileServiceClient = profileServiceClient;
    }

    @GetMapping("/turnovers")
    public List<GetAllTurnoversResponse> getAllTurnovers() {
        List<ContractModel> contracts = contractService.findAllTurnovers(onlineUser.getUserId());
        return contracts
                .stream()
                .map(ContractMapper::mapToTurnover)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}/sides")
    public GetContractSidesResponse getContractSides(@PathVariable @Valid Long id) {
        BinaryDataHolder<User, User> users = contractService.getContractSides(id);
        return new GetContractSidesResponse(users.getFirstType(), users.getSecondType());
    }

    @GetMapping
    public List<GetAllContractsResponse> getAll(
            @RequestParam(required = false, defaultValue = "1") @Min(1) Integer page,
            @RequestParam(required = false, defaultValue = "25") @Max(200) Integer pageSize) {
        return contractService.findAllContractModels(onlineUser.getUserId(),
                List.of(ContractState.values())
                        .stream()
                        .filter(contractState ->
                                !List.of(ContractState.FEE_DEPOSIT_END,
                                        ContractState.DELIVERY_DONE,
                                        ContractState.CONTRACT_END,
                                        ContractState.STALE,
                                        ContractState.BUYER_APPROVAL)
                                        .contains(contractState))
                        .collect(Collectors.toList()),
                page,
                pageSize)
                .stream()
                .map(ContractMapper::map)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public GetContractResponse get(@PathVariable @NotNull Long id) {
        return ContractMapper.map(contractService.findById(id));
    }

    //TODO: @Siavash change confirm methods to post
    @PutMapping("/commodities/confirm")
    @Created
    public ConfirmSuggestionResponse confirmCommoditySuggestion(
            @Valid @RequestBody ConfirmCommodityContractTemplateRequest request) {

        Contract contract = contractService.confirmAndSaveCommodity(
                request.getContractTemplateId(),
                request.getSuggestionId(),
                request.getTitle(),
                onlineUser.getUserId(),
                request.getPrice(),
                request.getTerminationPenalty(),
                fromMillis(request.getDeliveryDate()),
                request.getProvince(),
                request.getCity(),
                request.getNeighbourhood(),
                request.getDescription(),
                PaymentMapper.mapToModels(request.getPaymentTemplates()),
                request.getFileIds(),
                request.getPricePerUnit(),
                request.getQuantity(),
                request.getUnit(),
                request.getOtherAttachments(),
                request.getSignDescription());

        return new ConfirmSuggestionResponse(contract.getId(), contract.getState());
    }

    @Created
    @PutMapping("/vehicles/confirm")
    public ConfirmSuggestionResponse confirmVehicleSuggestion(
            @Valid @RequestBody ConfirmVehicleContractTemplateRequest request) {

        Contract contract = contractService.confirmAndSaveVehicle(
                request.getContractTemplateId(),
                request.getSuggestionId(),
                request.getTitle(),
                onlineUser.getUserId(),
                request.getPrice(),
                request.getTerminationPenalty(),
                fromMillis(request.getDeliveryDate()),
                request.getProvince(),
                request.getCity(),
                request.getNeighbourhood(),
                request.getDescription(),
                PaymentMapper.mapToModels(request.getPaymentTemplates()),
                request.getFileIds(),
                request.getManufactureYear(),
                request.getUsage(),
                request.getGearbox(),
                request.getFuel(),
                request.getBodyStatus(),
                request.getColor(),
                ContractMapper.map(request.getVehicleAttachment()),
                request.getIsOwner(),
                request.getVehicleAttorneyAttachments(),
                request.getOtherAttachments(),
                request.getSignDescription());

        return new ConfirmSuggestionResponse(contract.getId(), contract.getState());
    }

    @Created
    @PutMapping("/services/confirm")
    public ConfirmSuggestionResponse confirmServiceSuggestion(
            @Valid @RequestBody ConfirmServiceContractTemplateRequest request) {

        Contract contract = contractService.confirmAndSaveService(
                request.getContractTemplateId(),
                request.getSuggestionId(),
                request.getTitle(),
                onlineUser.getUserId(),
                request.getPrice(),
                request.getTerminationPenalty(),
                fromMillis(request.getDeliveryDate()),
                request.getProvince(),
                request.getCity(),
                request.getNeighbourhood(),
                request.getDescription(),
                PaymentMapper.mapToModels(request.getPaymentTemplates()),
                request.getFileIds(),
                request.getOtherAttachments(),
                request.getSignDescription());

        return new ConfirmSuggestionResponse(contract.getId(), contract.getState());
    }

    @PutMapping("/{contractId}/sign")
    @NoContent
    public void signContractByBuyer(@PathVariable @NotNull Long contractId) {
        contractService.signContractByBuyer(contractId, onlineUser.getUserId());
    }

    @NoContent
    @PutMapping("/{contractId}/reject")
    public void rejectContractByBuyer(@PathVariable @NotNull Long contractId) {
        contractService.rejectContractByBuyer(contractId, onlineUser.getUserId());
    }

    @GetMapping("/{contractId}/status")
    public ContractStatusResponse getContractStatus(@PathVariable @NotNull Long contractId) {
        return ContractMapper.map(contractService.getContractStatus(contractId, onlineUser.getUserId()));
    }

    @PostMapping("/{contractId}/payment/{amount}")
    @NoContent
    public void payContractPayment(@Valid @PathVariable Long contractId, @Valid @PathVariable Long amount) {
        contractService.doPayment(contractId, amount);
    }

    @GetMapping("/{contractId}/payment/inquiry")
    public PaymentInquiryResponse inquiryPayment(@Valid @PathVariable Long contractId) {
        return new PaymentInquiryResponse(contractService.paymentInquiry(onlineUser.getUserId(), contractId));
    }

    @NoContent
    @PutMapping("/deposit")
    public void depositContractFee(@Valid @RequestBody DepositContractFeeRequest request) {
        contractService.depositContractFee(
                request.getContractId(),
                request.getSuccess(),
                request.getDepositDate());
    }

    @GetMapping("/{contractId}/qrcode")
    public ResponseEntity<Resource> downloadQrCode(@PathVariable @NotNull Long contractId) {

        QrUtil.generate(contractId);

//        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
        File file = new File("/var/vsq/others/" + "contract-" + contractId + ".png");
        InputStreamResource resource;
        try {
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
            throw new FileNotFoundException();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }

    @GetMapping("/qrcode/{qrcode}/info")
    public InquiryQrCodeInfoResponse inquiryQrCodeInfo(@PathVariable @NotNull String qrcode) {
        try {
            String[] parts = qrcode.split("_");

            Contract contract = contractService.findById(Long.valueOf(parts[parts.length - 1]));

            User user = profileServiceClient.findById(contract.getSellerId());

            List<Long> files = FileAddressMapper.map(contract.getFiles(), FileType.PRODUCT_IMAGE);

            return new InquiryQrCodeInfoResponse(
                    contract.getId(),
                    contract.getTitle(),
                    contract.getPrice(),
                    files.isEmpty() ? null : files.get(0),
                    nowInMillis(),
                    contract.getBuyerApproval().getDueDate().getTime(),
                    user);
        } catch (Throwable throwable) {
            throw new QrCodeNotFoundException();
        }

    }

    @NoContent
    @PutMapping("/{contractId}/deliver")
    public void deliverContractSubjectBySeller(@PathVariable @NotNull Long contractId) {
        contractService.deliverContract(contractId, onlineUser.getUserId());
    }

    // todo : just for testing purposes!! should be completed Later after Analysis
    @NoContent
    @PutMapping("/{contractId}/seal-of-approval/{isApproved}")
    public void sealOfApprovalByBuyer(@PathVariable @NotNull Long contractId,
                                      @PathVariable @NotNull Boolean isApproved) {
        contractService.sealOfApprovalByBuyer(contractId,
                onlineUser.getUserId(),
                isApproved);
    }
}

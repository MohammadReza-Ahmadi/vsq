package com.vosouq.contract.controller.mapper;

import com.vosouq.commons.model.kafka.ContractPaymentPayload;
import com.vosouq.commons.model.kafka.PaymentPayloadStatus;
import com.vosouq.contract.controller.dto.PaymentResponse;
import com.vosouq.contract.controller.dto.PaymentTemplateRequest;
import com.vosouq.contract.model.Contract;
import com.vosouq.contract.model.Payment;
import com.vosouq.contract.model.PaymentTemplateModel;
import com.vosouq.contract.utills.TimeUtil;

import java.util.List;
import java.util.stream.Collectors;

public class PaymentMapper {

    public static List<PaymentResponse> map(List<Payment> payments) {
        return payments
                .stream()
                .map(payment -> new PaymentResponse(payment.getAmount(), payment.getDueDate().getTime()))
                .collect(Collectors.toList());
    }

    public static List<PaymentTemplateModel> mapToModels(List<PaymentTemplateRequest> requests) {
        return requests
                .stream()
                .map(request -> new PaymentTemplateModel(request.getAmount(), TimeUtil.fromMillis(request.getDueDate())))
                .collect(Collectors.toList());

    }

    public static ContractPaymentPayload convertToPaymentPayload(Payment payment) {
        ContractPaymentPayload paymentPayload = new ContractPaymentPayload();
        paymentPayload.setPaymentId(payment.getId());
        paymentPayload.setAmount(payment.getAmount());
        paymentPayload.setContractId(payment.getContract().getId());
        paymentPayload.setCreateDate(payment.getCreateDate());
        paymentPayload.setDueDate(payment.getDueDate());
        paymentPayload.setIsDelayed(payment.getIsDelayed());
        paymentPayload.setPaymentDate(payment.getPaymentDate());
        paymentPayload.setStatus(PaymentPayloadStatus.valueOf(payment.getStatus().toString()));
        paymentPayload.setUpdateDate(payment.getUpdateDate());
        return paymentPayload;
    }


}

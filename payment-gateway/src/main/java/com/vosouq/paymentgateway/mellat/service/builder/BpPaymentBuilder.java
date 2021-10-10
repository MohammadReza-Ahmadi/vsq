package com.vosouq.paymentgateway.mellat.service.builder;

import com.vosouq.paymentgateway.mellat.controller.dto.GetIpgPaymentRefIdRequest;
import com.vosouq.paymentgateway.mellat.enumeration.BpRequestStatus;
import com.vosouq.paymentgateway.mellat.model.ipg.BpPayment;
import com.vosouq.paymentgateway.mellat.model.ipg.OrderSequence;

import java.util.Date;

public final class BpPaymentBuilder {

    public static BpPayment build(GetIpgPaymentRefIdRequest getIpgPaymentRefIdRequest, BpRequestStatus status) {
        BpPayment bpPayment = new BpPayment();
        bpPayment.setOrderSequence(new OrderSequence());
        bpPayment.setRequesterId(getIpgPaymentRefIdRequest.getRequesterId());
        bpPayment.setStatus(status);
        bpPayment.setLocalDateTime(new Date());
        bpPayment.setAmount(getIpgPaymentRefIdRequest.getAmount());
//        bpPayment.setAdditionalData(gatewayPaymentRequest.getAdditionalData());
        return bpPayment;
    }
}

package com.vosouq.bookkeeping.controller.dto.requests;

import com.vosouq.bookkeeping.enumeration.PaymentType;
import com.vosouq.bookkeeping.enumeration.RequesterType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * This object fills by Payment requester
 * and send as a rest request BodyParameter to payment-bookkeeping microservice
 * */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class
PayRequest {
    /**
     * Payment request unique id line: contractId in contract microservice
     * */
    private Long requesterId;
    /**
     * Payer of money user id
     * */
    private Long payerId;

    /**
     * Recipient of money user id
     * */
    private Long recipientId;

    private RequesterType requesterType;
    private PaymentType paymentType;
    private BigDecimal amount;
    private Long orderId;
    private String mobileNo;
    private String description;
}

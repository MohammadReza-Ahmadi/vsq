package com.vosouq.paymentgateway.mellat.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookkeepingPaymentRequestResponse {
    private BigDecimal amount;
    private String mobileNo;
    private String additionalData;
}

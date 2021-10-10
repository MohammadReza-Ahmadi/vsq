package com.vosouq.bookkeeping.controller.dto.requests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IBANPaymentRequest {
    private Long requesterId;
    private Long orderId;
    private String iban;
    private BigDecimal amount;
    private String mobileNo;
}

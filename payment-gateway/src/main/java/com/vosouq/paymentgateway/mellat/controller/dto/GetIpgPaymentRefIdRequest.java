package com.vosouq.paymentgateway.mellat.controller.dto;

import lombok.*;

import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetIpgPaymentRefIdRequest {
    private Long requesterId;
    private BigDecimal amount;
    private String mobileNo;
}

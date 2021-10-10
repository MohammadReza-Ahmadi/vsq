package com.vosouq.bookkeeping.controller.dto.requests;

import lombok.*;

import java.math.BigDecimal;

/***
 * This object provides for calling payment-gateway microservice
 * and generates from PayRequest object which should be send
 * from payment requester
 * this object fields is based on business communication role between payment-bookkeeping and payment-gateway microservices
 * */

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
@Setter
public class GetIpgPaymentRefIdRequest {
    @NonNull
    private Long requesterId;
    @NonNull
    private BigDecimal amount;
    private String mobileNo;
}

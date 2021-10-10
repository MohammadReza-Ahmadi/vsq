package com.vosouq.bookkeeping.controller.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayResponse {
    private Long paymentRequestId;
    private String gatewayRefId;
    private String mobileNo;
}

package com.vosouq.bookkeeping.controller.dto.responses;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class BookkeepingPaymentRequestResponse {
    @NonNull
    private BigDecimal amount;
    private String mobileNo;
    private String additionalData;
}

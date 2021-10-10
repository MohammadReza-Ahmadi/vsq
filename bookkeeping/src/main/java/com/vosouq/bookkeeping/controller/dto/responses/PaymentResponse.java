package com.vosouq.bookkeeping.controller.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {
    private Long paymentId;
    private Long requesterId;
    private String paymentStatus;
    private BigDecimal amount;
    private String requesterType;
    private String paymentType;
    private String gatewayType;
    private Long requestDate;
    private String description;
}

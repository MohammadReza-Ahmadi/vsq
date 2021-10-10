package com.vosouq.contract.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum PaymentStatus {
    PAID, UNPAID;
}

package com.vosouq.bookkeeping.controller.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountWithdrawalRequest {
    private String iban;
    private Long amount;
}


package com.vosouq.bookkeeping.controller.dto.responses;

import com.vosouq.bookkeeping.enumeration.AccountTransactionType;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@RequiredArgsConstructor
@Getter
@Setter
public class AccountTurnoverResponse {
    @NonNull
    private AccountTransactionType type;
    @NonNull
    private Long amount;
    @NonNull
    private Date date;

    private String description;
}

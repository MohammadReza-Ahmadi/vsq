package com.vosouq.contract.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum ContractState {

    WAIT_FOR_SIGNING,
    SIGN_BY_SELLER,
    SIGN_BY_BUYER,
    PAYMENTS_IN_PROGRESS,
    PAYMENTS_DONE,
    DELIVERY_IN_PROGRESS,
    DELIVERY_DONE,
    BUYER_APPROVAL,
    FEE_DEPOSIT_END, // it is equivalent with contract end
    CONTRACT_END, // for view purposes
    REJECTED_BY_BUYER,
    STALE

}

package com.vosouq.contract.model;

public enum ActionState {
    WAITING_FOR_SIGN,
    SIGNED,
    WAITING_FOR_PAYMENT,
    PAID,
    WAITING_FOR_DELIVERY,
    DELIVERED,
    END_OF_CONTRACT

}
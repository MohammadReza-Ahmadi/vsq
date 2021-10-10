package com.vosouq.bookkeeping.enumeration;

import static com.vosouq.bookkeeping.constant.NumberConstants.*;

public enum AccountTransactionType {
    WITHDRAWAL(ZERO_INT),
    DEPOSIT(ONE_INT),
    BLOCK(TWO_INT),
    UNBLOCK(THREE_INT);

    private int code;

    AccountTransactionType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public boolean isDeposit() {
        return this.equals(DEPOSIT);
    }

    public boolean isWithdrawal() {
        return this.equals(WITHDRAWAL);
    }

    public static AccountTransactionType resolve(int code){
        for (AccountTransactionType value : AccountTransactionType.values()) {
            if(value.getCode()==code)
                return value;
        }
        throw new IllegalArgumentException("AccountTransactionType by code:"+code+" is not defined!");
    }
}

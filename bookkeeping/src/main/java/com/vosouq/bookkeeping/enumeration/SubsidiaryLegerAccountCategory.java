package com.vosouq.bookkeeping.enumeration;

public enum SubsidiaryLegerAccountCategory {
    EXCHANGE_TRADABLE_ACCOUNT_OF_CUSTOMERS(2);

    private int code;

    SubsidiaryLegerAccountCategory(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

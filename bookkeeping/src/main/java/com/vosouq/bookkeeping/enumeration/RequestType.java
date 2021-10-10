package com.vosouq.bookkeeping.enumeration;

public enum RequestType {
    ACCOUNT_RECHARGE(1),
    CONTRACT_CREATE(2),
    CONTRACT_PAYMENT(3),
    CONTRACT_GOODS_DELIVERY(4),
    CONTRACT_SETTLEMENT(5),
    ACCOUNT_WITHDRAWAL(6);

    private int code;

    RequestType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public boolean isAccountRecharge(){
        return this.equals(ACCOUNT_RECHARGE);
    }

    public boolean isContractPayment(){
        return this.equals(CONTRACT_PAYMENT);
    }
}

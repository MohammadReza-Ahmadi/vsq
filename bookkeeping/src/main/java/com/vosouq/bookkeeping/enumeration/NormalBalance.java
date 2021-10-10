package com.vosouq.bookkeeping.enumeration;

public enum NormalBalance {
    DEBIT,
    CREDIT,
    NO_NATURE;

    public boolean isCredit(){
        return this.equals(CREDIT);
    }

    public boolean isDebit(){
        return this.equals(DEBIT);
    }
}

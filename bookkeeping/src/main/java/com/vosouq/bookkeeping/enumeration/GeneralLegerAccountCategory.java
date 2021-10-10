package com.vosouq.bookkeeping.enumeration;

public enum GeneralLegerAccountCategory {
    ASSETS(1, NormalBalance.DEBIT),
    LIABILIIES(2, NormalBalance.CREDIT),
    EQUITIES(3, NormalBalance.CREDIT),
    REVENUE(4, NormalBalance.CREDIT),
    GAINS(5, NormalBalance.CREDIT),
    EXPENSES(6, NormalBalance.DEBIT),
    LOSSES(7, NormalBalance.DEBIT),
    CONTROL_ACCOUNTS(8, NormalBalance.NO_NATURE);

    private Integer code;
    private NormalBalance normalBalance;

    GeneralLegerAccountCategory(Integer code, NormalBalance normalBalance) {
        this.code = code;
        this.normalBalance = normalBalance;
    }

    public static GeneralLegerAccountCategory resolve(Integer code) {
        for (GeneralLegerAccountCategory value : values()) {
            if (value.code.equals(code))
                return value;
        }

        throw new IllegalStateException("GeneralLegerAccountCategory is not defined by code:" + code);
    }

    public Integer getCode() {
        return code;
    }

    public NormalBalance getNormalBalance() {
        return normalBalance;
    }
}

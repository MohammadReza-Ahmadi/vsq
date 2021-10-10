package com.vosouq.bookkeeping.model.posting;

public enum VoucherFormula {
    AMOUNT('a'),
    COMMISSION('c'),
    CONTROL('e'),
    FORMULA('f'),
    VAT('v'),
    REMAIN_AMOUNT('r');

    private Character formula;

    VoucherFormula(Character formula) {
        this.formula = formula;
    }

    public static VoucherFormula resolve(Character formula) {
        for (VoucherFormula value : values()) {
            if (value.formula.equals(formula))
                return value;
        }

        throw new IllegalArgumentException("VoucherFormula by formula:[" + formula + "] is not defined! ");
    }

    public boolean isAmount() {
        return this.equals(AMOUNT);
    }

    public boolean isCommission() {
        return this.equals(COMMISSION);
    }

    public boolean isControl() {
        return this.equals(CONTROL);
    }

    public boolean isFormula() {
        return this.equals(FORMULA);
    }

    public boolean isVAT() {
        return this.equals(VAT);
    }

    public boolean isRemainAmount() {
        return this.equals(REMAIN_AMOUNT);
    }
}

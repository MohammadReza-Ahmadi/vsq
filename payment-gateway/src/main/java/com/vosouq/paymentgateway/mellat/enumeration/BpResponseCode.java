package com.vosouq.paymentgateway.mellat.enumeration;


public enum BpResponseCode {
    FAILED("1"),
    SUCCESS("0"),
    CANCEL("17"),
    //todo: temp solution, should be implement list of error codes later
    ERROR("-1");

    private String code;

    BpResponseCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static BpResponseCode resolve(String code) {
        for (BpResponseCode bpResponseCode : BpResponseCode.values()) {
            if (bpResponseCode.code.equals(code))
                return bpResponseCode;
        }

        return ERROR;
//        throw new IllegalArgumentException("BpResponseCode by code:[" + code + "] not found!");
    }

    public boolean isSuccess() {
        return this.equals(BpResponseCode.SUCCESS);
    }

    public boolean isFailed() {
        return this.equals(BpResponseCode.FAILED);
    }

    public boolean isCancel() {
        return this.equals(BpResponseCode.CANCEL);
    }

    public boolean isError() {
        return this.equals(BpResponseCode.ERROR);
    }

    public boolean isNotError() {
        return !isError();
    }
}

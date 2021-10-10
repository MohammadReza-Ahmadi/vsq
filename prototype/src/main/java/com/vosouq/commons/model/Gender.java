package com.vosouq.commons.model;

public enum Gender {
    MALE(1), FEMALE(2);
    private int code;

    private Gender(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public Gender getByCode(int code) {
        switch (code) {
            case 1:
                return MALE;
            case 2:
                return FEMALE;
            default:
                throw new RuntimeException();
        }
    }
}

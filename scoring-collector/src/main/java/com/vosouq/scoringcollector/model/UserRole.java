package com.vosouq.scoringcollector.model;

public enum UserRole {
    SELLER('S'),
    BUYER('B');

    private char code;

    UserRole(char code) {
        this.code = code;
    }

    public static UserRole resolve(char code) {
        for (UserRole role : values()) {
            if (role.code == code)
                return role;
        }
        throw new IllegalArgumentException("UserRole by code:" + code + " is not defined!");
    }

    public char getCode() {
        return code;
    }
}

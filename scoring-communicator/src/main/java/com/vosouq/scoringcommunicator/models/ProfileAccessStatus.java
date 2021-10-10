package com.vosouq.scoringcommunicator.models;

public enum ProfileAccessStatus {
    WAITING(0),
    GRANTED(1),
    LIMITED(2);

    private int code;

    ProfileAccessStatus(int code) {
        this.code = code;
    }

    public static ProfileAccessStatus resolve(int code) {
        for (ProfileAccessStatus v : ProfileAccessStatus.values()) {
            if (v.code == code)
                return v;
        }
        throw new IllegalArgumentException("ProfileAccessStatus by code" + code + " is not found!");
    }
}

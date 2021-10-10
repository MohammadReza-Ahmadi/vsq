package com.vosouq.bookkeeping.enumeration;

public enum RequestStatus {
    PENDING,
    CANCEL,
    FAILED,
    SUCCESS;

    public boolean isSuccess() {
        return this.equals(SUCCESS);
    }
}

package com.vosouq.bookkeeping.exception;

import com.vosouq.commons.exception.VosouqBaseException;

public class VosouqBadRequestException extends VosouqBaseException {

    public VosouqBadRequestException(String... parameters) {
        super(parameters);
    }

    public VosouqBadRequestException(Throwable cause, String... parameters) {
        super(cause, parameters);
    }
}

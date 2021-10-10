package com.vosouq.commons.exception;

public class NotFoundException extends VosouqBaseException {

    public NotFoundException(String... parameters) {
        super(parameters);
    }

    public NotFoundException(Throwable cause, String... parameters) {
        super(cause, parameters);
    }
}

package com.vosouq.commons.exception;

public class BusinessException extends VosouqBaseException {

    public BusinessException(String... parameters) {
        super(parameters);
    }

    public BusinessException(Throwable cause, String... parameters) {
        super(cause, parameters);
    }
}

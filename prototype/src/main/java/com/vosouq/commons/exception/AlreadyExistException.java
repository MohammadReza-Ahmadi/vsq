package com.vosouq.commons.exception;

public class AlreadyExistException extends VosouqBaseException {

    public AlreadyExistException(String... parameters) {
        super(parameters);
    }

    public AlreadyExistException(Throwable cause, String... parameters) {
        super(cause, parameters);
    }
}

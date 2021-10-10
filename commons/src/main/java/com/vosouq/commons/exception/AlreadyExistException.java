package com.vosouq.commons.exception;

import java.util.Map;

public class AlreadyExistException extends VosouqBaseException {

    public AlreadyExistException(String... parameters) {
        super(parameters);
    }

    public AlreadyExistException(Map<String, String> extraParams, String... parameters) {
        super(extraParams, parameters);
    }

    public AlreadyExistException(Throwable cause, String... parameters) {
        super(cause, parameters);
    }

    public AlreadyExistException(Throwable cause, Map<String, String> extraParams, String... parameters) {
        super(cause, extraParams, parameters);
    }
}

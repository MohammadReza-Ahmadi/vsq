package com.vosouq.commons.exception;

import java.util.Map;

public class NotFoundException extends VosouqBaseException {

    public NotFoundException(String... parameters) {
        super(parameters);
    }

    public NotFoundException(Map<String, String> extraParams, String... parameters) {
        super(extraParams, parameters);
    }

    public NotFoundException(Throwable cause, String... parameters) {
        super(cause, parameters);
    }

    public NotFoundException(Throwable cause, Map<String, String> extraParams, String... parameters) {
        super(cause, extraParams, parameters);
    }
}

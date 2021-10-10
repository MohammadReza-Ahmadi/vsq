package com.vosouq.commons.exception;

import java.util.Map;

public class BusinessException extends VosouqBaseException {

    public BusinessException(String... parameters) {
        super(parameters);
    }

    public BusinessException(Map<String, String> extraParams, String... parameters) {
        super(extraParams, parameters);
    }

    public BusinessException(Throwable cause, String... parameters) {
        super(cause, parameters);
    }

    public BusinessException(Throwable cause, Map<String, String> extraParams, String... parameters) {
        super(cause, extraParams, parameters);
    }
}

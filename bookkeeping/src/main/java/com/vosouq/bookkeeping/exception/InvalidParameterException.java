package com.vosouq.bookkeeping.exception;

import com.vosouq.commons.exception.BusinessException;

public class InvalidParameterException extends BusinessException {

    public InvalidParameterException(String... parameters) {
        super(parameters);
    }
}

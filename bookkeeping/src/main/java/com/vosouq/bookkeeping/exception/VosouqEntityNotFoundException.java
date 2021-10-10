package com.vosouq.bookkeeping.exception;

import com.vosouq.commons.exception.BusinessException;

public class VosouqEntityNotFoundException extends BusinessException {

    public VosouqEntityNotFoundException(String... parameters) {
        super(parameters);
    }
}

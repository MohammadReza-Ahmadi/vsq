package com.vosouq.paymentgateway.mellat.exception;

import com.vosouq.commons.exception.BusinessException;

public class VosouqEntityNotFoundException extends BusinessException {

    public VosouqEntityNotFoundException(String... parameters) {
        super(parameters);
    }
}

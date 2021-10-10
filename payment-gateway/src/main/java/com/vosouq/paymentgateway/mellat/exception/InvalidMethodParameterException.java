package com.vosouq.paymentgateway.mellat.exception;

import com.vosouq.commons.exception.BusinessException;

public class InvalidMethodParameterException extends BusinessException {

    public InvalidMethodParameterException(String... parameters) {
        super(parameters);
    }
}

package com.vosouq.paymentgateway.mellat.exception;

import com.vosouq.commons.exception.BusinessException;

public class BookkeepingCallException extends BusinessException {

    public BookkeepingCallException(String... parameters) {
        super(parameters);
    }
}

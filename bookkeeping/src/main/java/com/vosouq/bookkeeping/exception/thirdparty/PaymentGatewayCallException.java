package com.vosouq.bookkeeping.exception.thirdparty;

import com.vosouq.commons.exception.BusinessException;

public class PaymentGatewayCallException extends BusinessException {

    public PaymentGatewayCallException(String... parameters) {
        super(parameters);
    }
}

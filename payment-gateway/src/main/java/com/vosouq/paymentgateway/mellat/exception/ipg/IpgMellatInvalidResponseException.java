package com.vosouq.paymentgateway.mellat.exception.ipg;

import com.vosouq.commons.exception.BusinessException;

public class IpgMellatInvalidResponseException extends BusinessException {

    public IpgMellatInvalidResponseException(String... parameters) {
        super(parameters);
    }
}

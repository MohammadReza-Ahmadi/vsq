package com.vosouq.paymentgateway.mellat.exception.ipg;

import com.vosouq.commons.exception.BusinessException;

public class IpgMellatVerifyFailedException extends BusinessException {

    public IpgMellatVerifyFailedException(String... parameters) {
        super(parameters);
    }
}

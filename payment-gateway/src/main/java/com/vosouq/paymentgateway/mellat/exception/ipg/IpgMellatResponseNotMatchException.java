package com.vosouq.paymentgateway.mellat.exception.ipg;

import com.vosouq.commons.exception.BusinessException;

public class IpgMellatResponseNotMatchException extends BusinessException {

    public IpgMellatResponseNotMatchException(String... parameters) {
        super(parameters);
    }
}

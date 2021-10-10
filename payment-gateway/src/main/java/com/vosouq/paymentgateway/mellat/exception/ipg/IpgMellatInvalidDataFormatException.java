package com.vosouq.paymentgateway.mellat.exception.ipg;

import com.vosouq.commons.exception.BusinessException;

public class IpgMellatInvalidDataFormatException extends BusinessException {

    public IpgMellatInvalidDataFormatException(String... parameters) {
        super(parameters);
    }
}

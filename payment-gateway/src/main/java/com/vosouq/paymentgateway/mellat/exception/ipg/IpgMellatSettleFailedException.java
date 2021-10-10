package com.vosouq.paymentgateway.mellat.exception.ipg;

import com.vosouq.commons.exception.BusinessException;

public class IpgMellatSettleFailedException extends BusinessException {

    public IpgMellatSettleFailedException(String... parameters) {
        super(parameters);
    }
}

package com.vosouq.bookkeeping.exception.iban;

import com.vosouq.commons.exception.BusinessException;

public class IbanValueIsEmptyException extends BusinessException {

    public IbanValueIsEmptyException(String... parameters) {
        super(parameters);
    }
}

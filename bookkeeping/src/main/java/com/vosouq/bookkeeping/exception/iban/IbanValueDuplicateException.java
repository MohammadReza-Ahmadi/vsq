package com.vosouq.bookkeeping.exception.iban;

import com.vosouq.commons.exception.BusinessException;

public class IbanValueDuplicateException extends BusinessException {

    public IbanValueDuplicateException(String... parameters) {
        super(parameters);
    }
}

package com.vosouq.bookkeeping.exception.iban;

import com.vosouq.commons.exception.BusinessException;

public class IbanNumberNotCorrectException extends BusinessException {

    public IbanNumberNotCorrectException(String... parameters) {
        super(parameters);
    }
}

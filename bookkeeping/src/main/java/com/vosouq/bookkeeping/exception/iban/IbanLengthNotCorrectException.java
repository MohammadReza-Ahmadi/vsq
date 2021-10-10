package com.vosouq.bookkeeping.exception.iban;

import com.vosouq.commons.exception.BusinessException;

public class IbanLengthNotCorrectException extends BusinessException {

    public IbanLengthNotCorrectException(String... parameters) {
        super(parameters);
    }
}

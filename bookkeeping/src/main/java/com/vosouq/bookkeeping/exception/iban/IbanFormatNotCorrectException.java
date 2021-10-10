package com.vosouq.bookkeeping.exception.iban;

import com.vosouq.commons.exception.BusinessException;

public class IbanFormatNotCorrectException extends BusinessException {

    public IbanFormatNotCorrectException(String... parameters) {
        super(parameters);
    }
}

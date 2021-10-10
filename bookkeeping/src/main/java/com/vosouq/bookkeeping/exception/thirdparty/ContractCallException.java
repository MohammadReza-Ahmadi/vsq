package com.vosouq.bookkeeping.exception.thirdparty;

import com.vosouq.commons.exception.BusinessException;

public class ContractCallException extends BusinessException {

    public ContractCallException(String... parameters) {
        super(parameters);
    }
}

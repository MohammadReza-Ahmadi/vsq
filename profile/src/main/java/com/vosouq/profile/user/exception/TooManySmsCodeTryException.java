package com.vosouq.profile.user.exception;

import com.vosouq.commons.exception.BusinessException;

import java.util.Map;

public class TooManySmsCodeTryException extends BusinessException {


    public TooManySmsCodeTryException() {
    }

    public TooManySmsCodeTryException(Map<String, String> extraParams, String... parameters) {
        super(extraParams, parameters);
    }
}

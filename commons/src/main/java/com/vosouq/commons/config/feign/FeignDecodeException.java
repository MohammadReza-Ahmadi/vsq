package com.vosouq.commons.config.feign;

import com.vosouq.commons.model.ErrorMessage;
import feign.Request;
import feign.codec.DecodeException;

public class FeignDecodeException extends DecodeException {

    private final ErrorMessage errorMessage;

    public FeignDecodeException(int status, String message, ErrorMessage errorMessage, Request request) {
        super(status, message, request);
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}

package com.vosouq.commons.exception;

import java.util.HashMap;
import java.util.Map;

public class VosouqBaseException extends RuntimeException {

    private String[] parameters;
    private Map<String, String> extraParams = new HashMap<>();

    public VosouqBaseException(String... parameters) {
        this.parameters = parameters;
    }

    public VosouqBaseException(Map<String, String> extraParams, String... parameters) {
        this.extraParams = extraParams;
        this.parameters = parameters;
    }

    public VosouqBaseException(Throwable cause, String... parameters) {
        super(cause);
        this.parameters = parameters;
    }

    public VosouqBaseException(Throwable cause, Map<String, String> extraParams, String... parameters) {
        super(cause);
        this.extraParams = extraParams;
        this.parameters = parameters;
    }

    public String[] getParameters() {
        return parameters;
    }

    public Map<String, String> getExtraParams() {
        return extraParams;
    }
}

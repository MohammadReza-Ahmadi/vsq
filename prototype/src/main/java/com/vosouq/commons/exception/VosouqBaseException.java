package com.vosouq.commons.exception;

public class VosouqBaseException extends RuntimeException {

    private String[] parameters;

    public VosouqBaseException(String... parameters) {
        this.parameters = parameters;
    }

    public VosouqBaseException(Throwable cause, String... parameters) {
        super(cause);
        this.parameters = parameters;
    }

    public String[] getParameters() {
        return parameters;
    }
}

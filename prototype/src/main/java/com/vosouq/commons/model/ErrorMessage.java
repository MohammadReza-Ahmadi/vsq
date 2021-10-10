package com.vosouq.commons.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

public class ErrorMessage {

    private String code;
    private String message;
    private String exception;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Field> fields = new ArrayList<>();

    public ErrorMessage() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public List<Field> getFields() {
        return fields;
    }

    public static class Field {
        private String name;
        private String message;
        private String value;

        public Field(String name, String message, String value) {
            this.name = name;
            this.message = message;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}

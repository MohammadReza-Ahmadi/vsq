package com.vosouq.commons.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ErrorMessage {

    private String code;
    private String message;
    private String exception;
    private List<ExtraParams> extraParams = new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Field> fields = new ArrayList<>();

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Field {
        private String name;
        private String message;
        private String value;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ExtraParams {
        private String key;
        private String value;
    }

}

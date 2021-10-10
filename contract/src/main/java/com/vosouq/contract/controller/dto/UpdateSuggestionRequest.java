package com.vosouq.contract.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class UpdateSuggestionRequest {

    @NotNull
    @ApiModelProperty(required = true)
    private Long price;
    private String description;
}
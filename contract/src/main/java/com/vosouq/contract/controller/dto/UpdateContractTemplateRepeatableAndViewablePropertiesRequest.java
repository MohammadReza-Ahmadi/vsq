package com.vosouq.contract.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateContractTemplateRepeatableAndViewablePropertiesRequest {
    @NotNull
    @ApiModelProperty(required = true)
    private Long templateId;
    @NotNull
    @ApiModelProperty(required = true)
    private Boolean repeatable;
    @Min(1)
    @ApiModelProperty(allowableValues = "greater than 1")
    private Integer numberOfRepeats;
    @NotNull
    @ApiModelProperty(required = true)
    private Boolean viewable;
}

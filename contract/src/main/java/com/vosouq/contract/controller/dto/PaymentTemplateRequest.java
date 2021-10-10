package com.vosouq.contract.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTemplateRequest {

    @NotNull
    @ApiModelProperty(required = true)
    private Long amount;
    @NotEmpty
    @ApiModelProperty(required = true, example = "1596539486")
    private Long dueDate;
}

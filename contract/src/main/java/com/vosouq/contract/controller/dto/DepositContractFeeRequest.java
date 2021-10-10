package com.vosouq.contract.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class DepositContractFeeRequest {
    @NotNull
    @ApiModelProperty(required = true)
    private Long contractId;
    @NotNull
    @ApiModelProperty(required = true)
    private Boolean success;
    private Long depositDate;
}
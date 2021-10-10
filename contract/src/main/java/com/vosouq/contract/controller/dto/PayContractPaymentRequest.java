package com.vosouq.contract.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class PayContractPaymentRequest {
    @NotNull
    @ApiModelProperty(required = true)
    private Long contractId;
    @NotNull
    @ApiModelProperty(required = true)
    private Long paymentId;
    private String paymentUUID;
    @NotNull
    @ApiModelProperty(required = true)
    private Boolean success;
    @NotNull
    @ApiModelProperty(required = true)
    private Long paymentDate;
}
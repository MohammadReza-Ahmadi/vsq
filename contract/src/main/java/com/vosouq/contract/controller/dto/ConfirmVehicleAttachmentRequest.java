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
public class ConfirmVehicleAttachmentRequest {

    @NotEmpty
    @ApiModelProperty(required = true, example = "58Y89511")
    private String barcodeNumber;

    @NotEmpty
    @ApiModelProperty(required = true, example = "20XF8H9109095")
    private String engineNumber;

    @NotEmpty
    @ApiModelProperty(required = true, example = "28766793")
    private String chassisNumber;

    @NotNull
    private Long cardFront;

    @NotNull
    private Long cardBack;

    @NotNull
    private Long document;
}
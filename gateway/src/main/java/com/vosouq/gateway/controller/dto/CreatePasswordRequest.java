package com.vosouq.gateway.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreatePasswordRequest {

    @NotNull
    @ApiModelProperty(required = true)
    private Long deviceId;
    @NotEmpty
    @ApiModelProperty(required = true)
    private String password;
    @NotEmpty
    @ApiModelProperty(required = true)
    private String confirmPassword;

}

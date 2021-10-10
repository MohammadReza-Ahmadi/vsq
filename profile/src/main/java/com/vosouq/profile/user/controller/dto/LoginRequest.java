package com.vosouq.profile.user.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LoginRequest {

    @NotNull
    @ApiModelProperty(required = true)
    private Long deviceId;
    @NotEmpty
    @ApiModelProperty(required = true)
    private String password;
    @NotEmpty
    @ApiModelProperty(required = true)
    private String bioToken;

}

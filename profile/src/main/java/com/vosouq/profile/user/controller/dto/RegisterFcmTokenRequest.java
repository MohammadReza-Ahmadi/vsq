package com.vosouq.profile.user.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class RegisterFcmTokenRequest {

    @NotEmpty
    @ApiModelProperty(required = true)
    private String token;

}

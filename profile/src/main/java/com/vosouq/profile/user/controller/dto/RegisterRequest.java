package com.vosouq.profile.user.controller.dto;

import com.vosouq.commons.validator.PhoneNumberConstraint;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class RegisterRequest {

    @NotEmpty
    @ApiModelProperty(required = true)
    private String udid;
    @NotEmpty
    @ApiModelProperty(required = true)
    private String deviceName;
    @NotEmpty
    @ApiModelProperty(required = true)
    private String os;
    @NotEmpty
    @ApiModelProperty(required = true)
    private String osVersion;
    @NotEmpty
    @ApiModelProperty(required = true)
    private String appVersion;
    @NotEmpty
    @PhoneNumberConstraint
    @ApiModelProperty(required = true, example = "+989121234567")
    private String phoneNumber;
}

package com.vosouq.profile.user.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BioLoginRequest {

    @NotNull
    @ApiModelProperty(required = true)
    private Long deviceId;
    @NotEmpty
    @ApiModelProperty(required = true)
    private String currentBioToken;
    @NotEmpty
    @ApiModelProperty(required = true)
    private String newBioToken;

}

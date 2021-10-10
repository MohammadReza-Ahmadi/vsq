package com.vosouq.prototype.user.controller.biz;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class UserExchangeRequest {

    @NotNull
    @ApiModelProperty(required = true)
    private Float rate;

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

}

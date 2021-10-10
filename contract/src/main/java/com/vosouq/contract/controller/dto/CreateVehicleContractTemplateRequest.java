package com.vosouq.contract.controller.dto;

import com.vosouq.contract.model.BodyStatus;
import com.vosouq.contract.model.Color;
import com.vosouq.contract.model.Fuel;
import com.vosouq.contract.model.Gearbox;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateVehicleContractTemplateRequest extends CreateContractTemplateRequest {

    @NotNull
    @ApiModelProperty(required = true)
    private Integer manufactureYear;
    @NotNull
    @ApiModelProperty(required = true)
    private Integer usage;
    private Gearbox gearbox;
    private Fuel fuel;
    private BodyStatus bodyStatus;
    private Color color;
    @NotNull
    @ApiModelProperty(required = true)
    private Boolean isOwner;

}

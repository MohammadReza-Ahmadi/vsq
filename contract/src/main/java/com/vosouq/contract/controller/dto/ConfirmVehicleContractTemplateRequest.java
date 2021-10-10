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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmVehicleContractTemplateRequest extends ConfirmContractTemplateRequest {

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
    @Valid
    private ConfirmVehicleAttachmentRequest vehicleAttachment;
    @NotNull
    @ApiModelProperty(required = true)
    private Boolean isOwner;
    private List<Long> vehicleAttorneyAttachments;
}
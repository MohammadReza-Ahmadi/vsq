package com.vosouq.contract.controller.dto;

import com.vosouq.contract.model.Unit;
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
public class UpdateCommodityContractTemplateRequest extends UpdateContractTemplateRequest {

    @NotNull
    @ApiModelProperty(required = true)
    private Long pricePerUnit;
    @NotNull
    @ApiModelProperty(required = true)
    private Integer quantity;
    @NotNull
    @ApiModelProperty(
            required = true,
            allowableValues = "[NUMBER, KILOGRAM, METER, TONNE, GRAM, MESQAL, SQUARE_METER, CENTIMETRE, LITRE, OTHER]")
    private Unit unit;
}
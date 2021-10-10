package com.vosouq.contract.controller.dto;

import com.vosouq.contract.model.BodyStatus;
import com.vosouq.contract.model.Color;
import com.vosouq.contract.model.Fuel;
import com.vosouq.contract.model.Gearbox;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleContractTemplateResponse extends GetContractTemplateResponse {

    private Integer manufactureYear;
    private Integer usage;
    private Gearbox gearbox;
    private Fuel fuel;
    private BodyStatus bodyStatus;
    private Color color;
    private Boolean isOwner;

    @Override
    public String toString() {
        return super.toString() +
                " VehicleContractTemplateResponse{" +
                "manufactureYear=" + manufactureYear +
                ", usage=" + usage +
                ", gearbox=" + gearbox +
                ", fuel=" + fuel +
                ", bodyStatus=" + bodyStatus +
                ", color=" + color +
                ", isOwner=" + isOwner +
                '}';
    }
}
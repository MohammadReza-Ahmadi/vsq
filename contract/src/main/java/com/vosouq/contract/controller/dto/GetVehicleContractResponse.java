package com.vosouq.contract.controller.dto;

import com.vosouq.contract.model.BodyStatus;
import com.vosouq.contract.model.Color;
import com.vosouq.contract.model.Fuel;
import com.vosouq.contract.model.Gearbox;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetVehicleContractResponse extends GetContractResponse {

    private Integer manufactureYear;
    private Integer usage;
    private Gearbox gearbox;
    private Fuel fuel;
    private BodyStatus bodyStatus;
    private Color color;
    private VehicleAttachmentResponse vehicleAttachment;
    private Boolean isOwner;
    private List<Long> vehicleAttorneyAttachments;
}

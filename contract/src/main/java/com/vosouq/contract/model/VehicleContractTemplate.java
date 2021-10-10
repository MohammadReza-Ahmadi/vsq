package com.vosouq.contract.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue(value = "VEHICLE")
public class VehicleContractTemplate extends ContractTemplate {

    @NotNull
    @Column(name = "manufacture_year")
    private Integer manufactureYear;

    @NotNull
    @Column(name = "`usage`")
    private Integer usage;

    @Column(name = "gearbox")
    @Enumerated(EnumType.STRING)
    private Gearbox gearbox;

    @Column(name = "fuel")
    @Enumerated(EnumType.STRING)
    private Fuel fuel;

    @Column(name = "body_status")
    @Enumerated(EnumType.STRING)
    private BodyStatus bodyStatus;

    @Column(name = "color")
    @Enumerated(EnumType.STRING)
    private Color color;

    @Column(name = "is_owner")
    private Boolean isOwner;

}

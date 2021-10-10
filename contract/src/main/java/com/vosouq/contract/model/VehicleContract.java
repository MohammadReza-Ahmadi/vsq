package com.vosouq.contract.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue(value = "VEHICLE")
public class VehicleContract extends Contract {

    @NotNull
    private Integer manufactureYear;

    @NotNull
    @Column(name = "`usage`")
    private Integer usage;

    @Enumerated(EnumType.STRING)
    private Gearbox gearbox;

    @Enumerated(EnumType.STRING)
    private Fuel fuel;

    @Enumerated(EnumType.STRING)
    private BodyStatus bodyStatus;

    @Enumerated(EnumType.STRING)
    private Color color;

    @OneToOne
    private VehicleAttachment vehicleAttachment;

    private Boolean isOwner;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "vehicle_attorney_files")
    private List<FileAddress> attorneyAttachments;

}

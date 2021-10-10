package com.vosouq.contract.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicle_attachment")
public class VehicleAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Timestamp createDate;

    private Timestamp updateDate;

    @NotNull
    private String barcodeNumber;

    @NotNull
    private String chassisNumber;

    @NotNull
    private String engineNumber;

    @NotNull
    @OneToOne
    private FileAddress cardFront;

    @NotNull
    @OneToOne
    private FileAddress cardBack;

    @NotNull
    @OneToOne
    private FileAddress document;
}
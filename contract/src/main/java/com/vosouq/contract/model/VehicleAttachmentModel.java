package com.vosouq.contract.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleAttachmentModel {

    private String barcodeNumber;
    private String chassisNumber;
    private String engineNumber;
    private Long cardFront;
    private Long cardBack;
    private Long document;
}
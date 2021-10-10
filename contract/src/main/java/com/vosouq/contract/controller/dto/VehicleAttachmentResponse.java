package com.vosouq.contract.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleAttachmentResponse {

    private String barcodeNumber;
    private String chassisNumber;
    private String engineNumber;
    private Long cardFront;
    private Long cardBack;
    private Long document;
}
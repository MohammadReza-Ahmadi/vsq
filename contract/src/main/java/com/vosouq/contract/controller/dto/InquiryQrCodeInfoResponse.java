package com.vosouq.contract.controller.dto;

import com.vosouq.contract.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InquiryQrCodeInfoResponse {

    private Long id;
    private String title;
    private Long price;
    private Long imageFile;
    private Long now;
    private Long dueDate;
    private User seller;
}

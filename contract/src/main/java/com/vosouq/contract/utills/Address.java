package com.vosouq.contract.utills;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum Address {

    IMAGES_CASE_FILE("/var/vsq/images/"),
    ATTACHMENTS_CASE_FILE("/var/vsq/attachments/"),
    OTHER_CASE_FILE("/var/vsq/others/");

    private String address;

    Address(String address) {
        this.address = address;
    }

    public void setAddress(String address){
        this.address = address;
    }

}
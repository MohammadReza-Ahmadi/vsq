package com.vosouq.contract.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum FileType {

    PRODUCT_IMAGE("PRODUCT"),
    ATTACHMENT_FILE("ATTACHMENT"),
    OTHER("OTHER");

    private String type;

    FileType(String type) {
        this.type = type;
    }
}
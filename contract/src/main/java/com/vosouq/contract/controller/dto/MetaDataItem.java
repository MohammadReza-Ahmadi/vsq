package com.vosouq.contract.controller.dto;

import com.vosouq.commons.util.MessageUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MetaDataItem {

    private String key;
    private String value;

    public static MetaDataItem build(String key) {
        return new MetaDataItem(key, MessageUtil.getMessage(key));
    }

}

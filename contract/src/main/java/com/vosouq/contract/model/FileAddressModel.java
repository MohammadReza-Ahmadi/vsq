package com.vosouq.contract.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileAddressModel {

    private Long id;
    private Timestamp createDate;
    private Timestamp updateDate;
    private Long owner;
    private String address;
    private FileType fileType;
    private String fileFormat;
}
package com.vosouq.contract.controller.mapper;

import com.vosouq.contract.model.FileAddress;
import com.vosouq.contract.model.FileType;

import java.util.List;
import java.util.stream.Collectors;

public class FileAddressMapper {

    public static List<Long> map(List<FileAddress> fileAddresses, FileType fileType) {
        return fileAddresses
                .stream()
                .filter(fileAddress -> fileAddress.getFileType().equals(fileType))
                .map(FileAddress::getId)
                .collect(Collectors.toList());
    }

    public static List<Long> map(List<FileAddress> fileAddresses) {
        return fileAddresses
                .stream()
                .map(FileAddress::getId)
                .collect(Collectors.toList());
    }

}
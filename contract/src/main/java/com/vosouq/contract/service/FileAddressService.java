package com.vosouq.contract.service;

import com.vosouq.contract.controller.dto.ResourceMetaData;
import com.vosouq.contract.model.FileAddress;
import com.vosouq.contract.model.FileType;
import com.vosouq.contract.utills.Address;
import org.springframework.web.multipart.MultipartFile;

public interface FileAddressService {

    Long upload(MultipartFile file,
                Long userId,
                Address addressToSave,
                FileType fileType);

    ResourceMetaData download(Long id);

    void save(FileAddress fileAddress);

    FileAddress findAndValidate(Long fileId, FileType fileType, Long userId);
}
package com.vosouq.contract.controller;

import com.vosouq.commons.annotation.Created;
import com.vosouq.commons.annotation.VosouqRestController;
import com.vosouq.commons.model.OnlineUser;
import com.vosouq.contract.controller.dto.FileAddressResponse;
import com.vosouq.contract.controller.dto.ResourceMetaData;
import com.vosouq.contract.exception.FileNotFoundException;
import com.vosouq.contract.exception.IncompatibleValueException;
import com.vosouq.contract.model.FileType;
import com.vosouq.contract.service.FileAddressService;
import com.vosouq.contract.utills.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.vosouq.contract.utills.Address.*;

@VosouqRestController
@RequestMapping("/files")
public class FileAddressController {

    private final FileAddressService fileAddressService;
    private final OnlineUser onlineUser;

    @Autowired
    public FileAddressController(FileAddressService fileAddressService, OnlineUser onlineUser) {
        this.fileAddressService = fileAddressService;
        this.onlineUser = onlineUser;
    }

    @PostMapping
    @Created
    public FileAddressResponse uploadFile(@RequestParam @Valid MultipartFile file,
                                          @RequestParam FileType fileType) {

        Address addressToSave;

        if (fileType.equals(FileType.PRODUCT_IMAGE))
            addressToSave = IMAGES_CASE_FILE;
        else if (fileType.equals(FileType.ATTACHMENT_FILE))
            addressToSave = ATTACHMENTS_CASE_FILE;
        else if (fileType.equals(FileType.OTHER))
            addressToSave = OTHER_CASE_FILE;
        else
            throw new IncompatibleValueException();

        return new FileAddressResponse(
                fileAddressService.upload(
                        file,
                        onlineUser.getUserId(),
                        addressToSave,
                        fileType));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable @NotNull Long id) {
        ResourceMetaData resourceMetaData = fileAddressService.download(id);
        if (resourceMetaData == null)
            throw new FileNotFoundException();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        if (resourceMetaData.getMediaType().includes(MediaType.APPLICATION_PDF))
            headers.add("Content-disposition", "attachment;");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resourceMetaData.getFileSize())
                .contentType(resourceMetaData.getMediaType())
                .body((InputStreamResource) resourceMetaData.getResource());
    }

}

package com.vosouq.contract.service.impl;

import com.vosouq.contract.controller.dto.ResourceMetaData;
import com.vosouq.contract.exception.FileNotFoundException;
import com.vosouq.contract.exception.IncompatibleValueException;
import com.vosouq.contract.model.FileAddress;
import com.vosouq.contract.model.FileType;
import com.vosouq.contract.repository.FileAddressRepository;
import com.vosouq.contract.service.FileAddressService;
import com.vosouq.contract.utills.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

import static com.vosouq.contract.utills.FileUtil.generateAddress;
import static com.vosouq.contract.utills.FileUtil.uploadFileHandler;
import static com.vosouq.contract.utills.TimeUtil.nowInTimestamp;

@Service
@Transactional
public class FileAddressServiceImpl implements FileAddressService {

    private final FileAddressRepository fileAddressRepository;

    @Autowired
    public FileAddressServiceImpl(FileAddressRepository fileAddressRepository) {
        this.fileAddressRepository = fileAddressRepository;
    }

    public Long upload(MultipartFile file,
                       Long userId,
                       Address addressToSave,
                       FileType fileType) {

        FileAddress fileAddress = new FileAddress();

        String generatedAddress = generateAddress(addressToSave, file);

        uploadFileHandler(generatedAddress, file);

        fileAddress.setCreateDate(nowInTimestamp());
        fileAddress.setFileType(fileType);
        fileAddress.setAddress(generatedAddress);
        fileAddress.setOwner(userId);
        fileAddress.setFileFormat(generatedAddress.substring(generatedAddress.lastIndexOf(".") + 1));

        if (!StringUtils.isEmpty(generatedAddress)) {
            fileAddressRepository.save(fileAddress);
        }

        return fileAddress.getId();
    }

    private FileAddress findById(Long id) {
        return fileAddressRepository
                .findById(id)
                .orElseThrow(FileNotFoundException::new);
    }

    @Override
    public ResourceMetaData download(Long id) {

        FileAddress fileAddress = findById(id);

        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
        File file = new File(fileAddress.getAddress());
        InputStreamResource resource;

        try {
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (java.io.FileNotFoundException e) {
            e.printStackTrace();
            throw new FileNotFoundException();
        }

        ResourceMetaData resourceMetaData = new ResourceMetaData();
        resourceMetaData.setResource(resource);
        resourceMetaData.setFileSize(file.length());

        if (fileAddress.getFileType() != null)
            resourceMetaData.setFileType(fileAddress.getFileType());

        if (fileAddress.getFileFormat() != null) {
            String fileFormat = fileAddress.getFileFormat();
            if (fileFormat.equals("jpeg") || fileFormat.equals("jpg"))
                mediaType = MediaType.IMAGE_JPEG;
            if (fileFormat.equals("png"))
                mediaType = MediaType.IMAGE_PNG;
            if (fileFormat.equals("pdf"))
                mediaType = MediaType.APPLICATION_PDF;
            resourceMetaData.setMediaType(mediaType);
        }

        return resourceMetaData;
    }

    @Override
    public void save(FileAddress fileAddress) {
        if (fileAddress != null)
            fileAddressRepository.save(fileAddress);
    }

    @Override
    public FileAddress findAndValidate(Long fileId, FileType fileType, Long userId) {

        FileAddress fileAddress = findById(fileId);

        if (!userId.equals(fileAddress.getOwner()) || !fileType.equals(fileAddress.getFileType()))
            throw new IncompatibleValueException();

        return fileAddress;
    }
}
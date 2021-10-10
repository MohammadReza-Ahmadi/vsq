package com.vosouq.contract.service.impl;

import com.vosouq.contract.model.FileType;
import com.vosouq.contract.model.VehicleAttachment;
import com.vosouq.contract.model.VehicleAttachmentModel;
import com.vosouq.contract.repository.VehicleAttachmentRepository;
import com.vosouq.contract.service.FileAddressService;
import com.vosouq.contract.service.VehicleAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.vosouq.contract.utills.TimeUtil.nowInTimestamp;

@Service
@Transactional
public class VehicleAttachmentServiceImpl implements VehicleAttachmentService {

    private final VehicleAttachmentRepository vehicleAttachmentRepository;
    private final FileAddressService fileAddressService;

    @Autowired
    public VehicleAttachmentServiceImpl(VehicleAttachmentRepository vehicleAttachmentRepository,
                                        FileAddressService fileAddressService) {
        this.vehicleAttachmentRepository = vehicleAttachmentRepository;
        this.fileAddressService = fileAddressService;
    }

    @Override
    public VehicleAttachment save(VehicleAttachmentModel model, Long contractSellerId) {
        VehicleAttachment entity = new VehicleAttachment();
        entity.setCreateDate(nowInTimestamp());
        if (model != null) {
            entity.setBarcodeNumber(model.getBarcodeNumber());
            entity.setChassisNumber(model.getChassisNumber());
            entity.setEngineNumber(model.getEngineNumber());
            entity.setCardFront(fileAddressService.findAndValidate(model.getCardFront(), FileType.ATTACHMENT_FILE, contractSellerId));
            entity.setCardBack(fileAddressService.findAndValidate(model.getCardBack(), FileType.ATTACHMENT_FILE, contractSellerId));
            entity.setDocument(fileAddressService.findAndValidate(model.getDocument(), FileType.ATTACHMENT_FILE, contractSellerId));
        }
        return vehicleAttachmentRepository.save(entity);
    }
}
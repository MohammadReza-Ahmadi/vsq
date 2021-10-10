package com.vosouq.contract.service;

import com.vosouq.contract.model.VehicleAttachment;
import com.vosouq.contract.model.VehicleAttachmentModel;

public interface VehicleAttachmentService {

    VehicleAttachment save(VehicleAttachmentModel model, Long contractSellerId);
}
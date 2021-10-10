package com.vosouq.contract.repository;

import com.vosouq.contract.model.VehicleAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleAttachmentRepository extends JpaRepository<VehicleAttachment, Long> {

}
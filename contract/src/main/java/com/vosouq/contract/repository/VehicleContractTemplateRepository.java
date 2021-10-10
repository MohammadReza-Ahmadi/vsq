package com.vosouq.contract.repository;

import com.vosouq.contract.model.VehicleContractTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleContractTemplateRepository extends JpaRepository<VehicleContractTemplate, Long> {

}

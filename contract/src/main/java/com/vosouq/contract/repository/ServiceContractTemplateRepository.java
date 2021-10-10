package com.vosouq.contract.repository;

import com.vosouq.contract.model.ServiceContractTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceContractTemplateRepository extends JpaRepository<ServiceContractTemplate, Long> {

}

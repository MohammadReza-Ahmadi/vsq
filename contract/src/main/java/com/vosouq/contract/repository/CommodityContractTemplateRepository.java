package com.vosouq.contract.repository;

import com.vosouq.contract.model.CommodityContractTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommodityContractTemplateRepository extends JpaRepository<CommodityContractTemplate, Long> {

}

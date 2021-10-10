package com.vosouq.contract.repository;

import com.vosouq.contract.model.CommodityContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommodityContractRepository extends JpaRepository<CommodityContract, Long> {

}

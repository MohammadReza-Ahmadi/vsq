package com.vosouq.contract.repository;

import com.vosouq.contract.model.VehicleContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleContractRepository extends JpaRepository<VehicleContract, Long> {

}

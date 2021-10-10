package com.vosouq.contract.repository;

import com.vosouq.contract.model.ServiceContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceContractRepository extends JpaRepository<ServiceContract, Long> {

}

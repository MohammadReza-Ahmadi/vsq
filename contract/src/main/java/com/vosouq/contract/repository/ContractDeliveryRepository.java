package com.vosouq.contract.repository;

import com.vosouq.contract.model.ContractDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractDeliveryRepository extends JpaRepository<ContractDelivery, Long> {
}
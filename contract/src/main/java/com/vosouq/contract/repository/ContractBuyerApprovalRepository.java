package com.vosouq.contract.repository;

import com.vosouq.contract.model.ContractBuyerApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractBuyerApprovalRepository extends JpaRepository<ContractBuyerApproval, Long> {
}
package com.vosouq.contract.service;

import com.vosouq.contract.model.Contract;
import com.vosouq.contract.model.ContractBuyerApproval;

import java.sql.Timestamp;

public interface ContractBuyerApprovalService {

    ContractBuyerApproval save(Contract contract, Timestamp dueDate);

    // todo : just for testing purposes!! should be completed Later after Analysis
    ContractBuyerApproval save(ContractBuyerApproval contractBuyerApproval);

    // todo : just for testing purposes!! should be completed Later after Analysis
    void sealOfApproval(ContractBuyerApproval contractBuyerApproval, Boolean isApproved);
}
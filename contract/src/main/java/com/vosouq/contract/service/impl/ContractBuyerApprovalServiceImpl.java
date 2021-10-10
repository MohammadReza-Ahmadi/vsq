package com.vosouq.contract.service.impl;

import com.vosouq.contract.exception.IncompatibleValueException;
import com.vosouq.contract.model.Contract;
import com.vosouq.contract.model.ContractBuyerApproval;
import com.vosouq.contract.repository.ContractBuyerApprovalRepository;
import com.vosouq.contract.service.ContractBuyerApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

import static com.vosouq.contract.utills.TimeUtil.nowInTimestamp;

@Service
@Transactional
public class ContractBuyerApprovalServiceImpl implements ContractBuyerApprovalService {
    private final ContractBuyerApprovalRepository contractBuyerApprovalRepository;

    @Autowired
    public ContractBuyerApprovalServiceImpl(ContractBuyerApprovalRepository contractBuyerApprovalRepository) {
        this.contractBuyerApprovalRepository = contractBuyerApprovalRepository;
    }

    @Override
    public ContractBuyerApproval save(Contract contract, Timestamp dueDate) {
        if (contract == null || dueDate == null)
            throw new IncompatibleValueException();

        ContractBuyerApproval buyerApproval = new ContractBuyerApproval();
        buyerApproval.setCreateDate(nowInTimestamp());
        buyerApproval.setDueDate(dueDate);
        return contractBuyerApprovalRepository.save(buyerApproval);
    }

    // todo : just for testing purposes!! should be completed Later after Analysis
    @Override
    public ContractBuyerApproval save(ContractBuyerApproval contractBuyerApproval) {
        if (contractBuyerApproval == null)
            throw new IncompatibleValueException();
        else
            return contractBuyerApprovalRepository.save(contractBuyerApproval);
    }

    // todo : just for testing purposes!! should be completed Later after Analysis
    @Override
    public void sealOfApproval(ContractBuyerApproval contractBuyerApproval, Boolean isApproved) {
        contractBuyerApproval.setUpdateDate(nowInTimestamp());
        if (isApproved) {
            contractBuyerApproval.setApprovedByBuyer(true);
            contractBuyerApproval.setApproveDate(nowInTimestamp());
        }
        save(contractBuyerApproval);
    }
}
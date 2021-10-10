package com.vosouq.contract.service.impl;

import com.vosouq.contract.exception.IncompatibleValueException;
import com.vosouq.contract.model.Contract;
import com.vosouq.contract.model.ContractDelivery;
import com.vosouq.contract.repository.ContractDeliveryRepository;
import com.vosouq.contract.service.ContractDeliveryService;
import com.vosouq.contract.utills.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
@Transactional
public class ContractDeliveryServiceImpl implements ContractDeliveryService {
    private final ContractDeliveryRepository contractDeliveryRepository;

    @Autowired
    public ContractDeliveryServiceImpl(ContractDeliveryRepository contractDeliveryRepository) {
        this.contractDeliveryRepository = contractDeliveryRepository;
    }

    @Override
    public ContractDelivery save(Contract contract, Timestamp dueDate) {
        if (dueDate == null)
            throw new IncompatibleValueException();

        ContractDelivery delivery = new ContractDelivery();
        delivery.setCreateDate(TimeUtil.nowInTimestamp());
        delivery.setDueDate(dueDate);
        delivery.setApprovedByBuyer(Boolean.FALSE);
        delivery.setContractId(contract.getId());

        return contractDeliveryRepository.save(delivery);
    }

    @Override
    public void deliverContractSubject(ContractDelivery contractDelivery) {
        contractDelivery.setUpdateDate(TimeUtil.nowInTimestamp());
        contractDelivery.setDeliveryDate(TimeUtil.nowInTimestamp());
        contractDelivery.setApprovedByBuyer(true);
        contractDeliveryRepository.save(contractDelivery);
    }

}
package com.vosouq.contract.service;

import com.vosouq.contract.model.Contract;
import com.vosouq.contract.model.ContractDelivery;

import java.sql.Timestamp;

public interface ContractDeliveryService {

    ContractDelivery save(Contract contract, Timestamp dueDate);

    void deliverContractSubject(ContractDelivery contractDelivery);
}
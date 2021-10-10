package com.vosouq.scoringcollector.service.storage.impl;

import com.vosouq.commons.model.kafka.ContractPayload;
import com.vosouq.commons.model.kafka.ContractPaymentPayload;
import com.vosouq.scoringcollector.model.payload.consuming.Contract;
import com.vosouq.scoringcollector.model.payload.consuming.ContractPayment;
import com.vosouq.scoringcollector.repository.ContractPaymentRepository;
import com.vosouq.scoringcollector.repository.ContractRepository;
import com.vosouq.scoringcollector.service.storage.ContractDataStorageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class ContractDataStorageServiceImpl implements ContractDataStorageService {

    private final ContractPaymentRepository contractPaymentRepository;
    private final ContractRepository contractRepository;
    private final MongoOperations mongoOperations;

    public ContractDataStorageServiceImpl(ContractPaymentRepository contractPaymentRepository, ContractRepository contractRepository, MongoOperations mongoOperations) {
        this.contractPaymentRepository = contractPaymentRepository;
        this.contractRepository = contractRepository;
        this.mongoOperations = mongoOperations;
    }

    @Override
    public void saveContractAndPaymentList(ContractPayload contractPayload) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // make a Contract
        Contract contract = new Contract();
        PropertyUtils.copyProperties(contract, contractPayload);

        // make a list of ContractPayment
        List<ContractPayment> contractPayments = new ArrayList<>();
        if (contractPayload.getPayments() != null) {
            for (ContractPaymentPayload paymentPayload : contractPayload.getPayments()) {
                ContractPayment contractPayment = new ContractPayment();
                PropertyUtils.copyProperties(contractPayment, paymentPayload);
                contractPayments.add(contractPayment);
            }
        }

        // persist contractPaymentList
        contractPaymentRepository.saveAll(contractPayments);
        log.info("ContractPaymentList data is extracted and save successfully.");

        // persist contract
        contract.setPaymentIds(contractPayments.stream().map(ContractPayment::getPaymentId).collect(Collectors.toList()));
        contractRepository.save(contract);
        log.info("Contract data is extracted and save successfully.");
    }

    @Override
    public void updateContractPayment(ContractPaymentPayload contractPaymentPayload) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ContractPayment contractPayment = new ContractPayment();
        PropertyUtils.copyProperties(contractPayment, contractPaymentPayload);
        Criteria criteria = new Criteria("paymentId").is(contractPayment.getPaymentId());
        mongoOperations.findAndReplace(new Query(criteria), contractPayment);
        log.info("ContractPayment data is extracted and update successfully.");
    }
}

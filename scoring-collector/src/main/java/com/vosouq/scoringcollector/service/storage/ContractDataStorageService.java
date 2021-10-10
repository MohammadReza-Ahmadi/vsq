package com.vosouq.scoringcollector.service.storage;

import com.vosouq.commons.model.kafka.ContractPayload;
import com.vosouq.commons.model.kafka.ContractPaymentPayload;

import java.lang.reflect.InvocationTargetException;

public interface ContractDataStorageService {

    void saveContractAndPaymentList(ContractPayload contractPayload) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    void updateContractPayment(ContractPaymentPayload paymentPayload) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;
}

package com.vosouq.scoringcollector.service.handler.raw;

import com.vosouq.commons.model.kafka.ContractPayload;
import com.vosouq.commons.model.kafka.ContractPaymentPayload;

import java.lang.reflect.InvocationTargetException;

public interface ContractRawDataHandler {

    void handleContractEvent(ContractPayload event) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    void handleContractPaymentEvent(ContractPaymentPayload event);
}

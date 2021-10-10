package com.vosouq.scoringcollector.service.handler.raw.impl;

import com.vosouq.commons.model.kafka.ContractPayload;
import com.vosouq.commons.model.kafka.ContractPaymentPayload;
import com.vosouq.scoringcollector.service.handler.raw.ContractRawDataHandler;
import com.vosouq.scoringcollector.service.handler.ready.DoneTradeReadyDataHandler;
import com.vosouq.scoringcollector.service.handler.ready.UndoneTradeReadyDataHandler;
import com.vosouq.scoringcollector.service.storage.ContractDataStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;

@Slf4j
@Service
@Transactional
public class ContractRawDataKafkaHandler implements ContractRawDataHandler {

    private final ContractDataStorageService contractDataStorageService;
    private final UndoneTradeReadyDataHandler undoneTradeReadyDataHandler;
    private final DoneTradeReadyDataHandler doneTradeReadyDataHandler;

    public ContractRawDataKafkaHandler(ContractDataStorageService contractDataStorageService, UndoneTradeReadyDataHandler undoneTradeReadyDataHandler, DoneTradeReadyDataHandler doneTradeReadyDataHandler) {
        this.contractDataStorageService = contractDataStorageService;
        this.undoneTradeReadyDataHandler = undoneTradeReadyDataHandler;
        this.doneTradeReadyDataHandler = doneTradeReadyDataHandler;
    }

    @Override
    public void handleContractEvent(ContractPayload event) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        contractDataStorageService.saveContractAndPaymentList(event);
        switch (event.getDueStatus()) {
            case STARTED:
                undoneTradeReadyDataHandler.handleContractCreateEvent(event);
                break;
            case ONGOING:
            case EXTENDED:
                break;
            case DONE:
                doneTradeReadyDataHandler.handleContractDoneEvent(event);
                break;
        }
    }

    @Override
    public void handleContractPaymentEvent(ContractPaymentPayload event) {

    }
}

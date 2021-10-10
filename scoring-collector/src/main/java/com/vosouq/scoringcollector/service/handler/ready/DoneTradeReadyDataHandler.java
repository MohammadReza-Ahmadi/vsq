package com.vosouq.scoringcollector.service.handler.ready;

import com.vosouq.commons.model.kafka.ContractPayload;

public interface DoneTradeReadyDataHandler {

    void handleContractDoneEvent(ContractPayload event);

}

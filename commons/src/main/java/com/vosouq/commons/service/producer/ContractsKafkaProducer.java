package com.vosouq.commons.service.producer;

import com.vosouq.commons.model.kafka.ContractPayload;

public interface ContractsKafkaProducer {

    void send(ContractPayload contractPayload);
}

package com.vosouq.commons.service.producer;

import com.vosouq.commons.model.kafka.ContractPaymentPayload;

public interface ContractPaymentsKafkaProducer {

    void send(ContractPaymentPayload contractPaymentPayload);
}

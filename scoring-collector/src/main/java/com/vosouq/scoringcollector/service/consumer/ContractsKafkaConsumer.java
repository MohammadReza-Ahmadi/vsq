package com.vosouq.scoringcollector.service.consumer;

import com.vosouq.commons.model.kafka.ContractPayload;
import com.vosouq.scoringcollector.service.handler.raw.ContractRawDataHandler;
import com.vosouq.scoringcollector.service.storage.ContractDataStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Slf4j
@Component
@ConditionalOnProperty(value = "kafka.contractsTopic.enabled")
public class ContractsKafkaConsumer {

    private final ContractRawDataHandler contractRawDataHandler;

    public ContractsKafkaConsumer(ContractRawDataHandler contractRawDataHandler) {
        this.contractRawDataHandler = contractRawDataHandler;
    }

    @KafkaListener(topics = "${kafka.contractsTopic.name}",
            groupId = "${kafka.groups.contracts.id:contractsGroup}",
            autoStartup = "true",
            containerFactory = "contractsKafkaListenerContainerFactory",
            properties = {"spring.json.trusted.packages:*"}
    )
    public void receiveContractPayload(@Payload ContractPayload data, @Headers MessageHeaders headers) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        log.info("received ContractPayload='{}'", data);
        contractRawDataHandler.handleContractEvent(data);
    }
}
package com.vosouq.scoringcollector.service.consumer;

import com.vosouq.commons.model.kafka.ContractPayload;
import com.vosouq.commons.model.kafka.ContractPaymentPayload;
import com.vosouq.scoringcollector.service.storage.ContractDataStorageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Slf4j
@Component
@ConditionalOnProperty(value = "kafka.contractPaymentsTopic.enabled")
public class ContractPaymentsKafkaConsumer {

    private final ContractDataStorageService contractDataStorageService;

    public ContractPaymentsKafkaConsumer(ContractDataStorageService contractDataStorageService) {
        this.contractDataStorageService = contractDataStorageService;
    }

    @KafkaListener(topics = "${kafka.contractPaymentsTopic.name}",
            groupId = "${kafka.groups.contractsTopic.id:contractsGroup}",
            autoStartup = "true",
            containerFactory = "contractPaymentsKafkaListenerContainerFactory",
            properties = {"spring.json.trusted.packages:*"})
    public void receiveContractPaymentPayload(@Payload ContractPaymentPayload data, @Headers MessageHeaders headers) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        log.info("received ContractPayload='{}'", data);
        contractDataStorageService.updateContractPayment(data);
    }
}
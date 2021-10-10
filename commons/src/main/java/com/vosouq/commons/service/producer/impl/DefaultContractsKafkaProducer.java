package com.vosouq.commons.service.producer.impl;

import com.vosouq.commons.config.kafka.topic.ContractsKafkaTopicsConfig;
import com.vosouq.commons.model.kafka.ContractPayload;
import com.vosouq.commons.service.producer.ContractsKafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@SuppressWarnings("unused")
@Component
@Slf4j
@ConditionalOnProperty(value = "kafka.contractsTopic.enabled")
public class DefaultContractsKafkaProducer implements ContractsKafkaProducer {

    private final ContractsKafkaTopicsConfig topicsConfig;
    private final KafkaTemplate<String, ContractPayload> contractsKafkaTemplate;

    public DefaultContractsKafkaProducer(ContractsKafkaTopicsConfig contractsKafkaTopicsConfig,
                                         KafkaTemplate<String, ContractPayload> contractsKafkaTemplate) {
        this.topicsConfig = contractsKafkaTopicsConfig;
        this.contractsKafkaTemplate = contractsKafkaTemplate;
    }

    @Override
    public void send(ContractPayload contractPayload) {
        DefaultContractsKafkaProducer.log.info("sending ContractPayload='{}' to topic='{}'", contractPayload, topicsConfig.getTopicName());
        Message<ContractPayload> message = MessageBuilder
                .withPayload(contractPayload)
                .setHeader(KafkaHeaders.TOPIC, topicsConfig.getTopicName())
                .build();

        contractsKafkaTemplate.send(message);
    }
}

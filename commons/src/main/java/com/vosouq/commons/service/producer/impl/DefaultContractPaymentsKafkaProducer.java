package com.vosouq.commons.service.producer.impl;

import com.vosouq.commons.config.kafka.topic.ContractPaymentsKafkaTopicsConfig;
import com.vosouq.commons.config.kafka.topic.ContractsKafkaTopicsConfig;
import com.vosouq.commons.model.kafka.ContractPaymentPayload;
import com.vosouq.commons.service.producer.ContractPaymentsKafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@SuppressWarnings("unused")
@Component
@Slf4j
@ConditionalOnProperty(value = "kafka.contractPaymentsTopic.enabled")
public class DefaultContractPaymentsKafkaProducer implements ContractPaymentsKafkaProducer {

    private final ContractPaymentsKafkaTopicsConfig topicsConfig;
    private final KafkaTemplate<String, ContractPaymentPayload> paymentsKafkaTemplate;

    public DefaultContractPaymentsKafkaProducer(ContractPaymentsKafkaTopicsConfig contractPaymentsKafkaTopicsConfig,
                                                KafkaTemplate<String, ContractPaymentPayload> paymentsKafkaTemplate) {
        this.topicsConfig = contractPaymentsKafkaTopicsConfig;
        this.paymentsKafkaTemplate = paymentsKafkaTemplate;
    }

    @Override
    public void send(ContractPaymentPayload contractPaymentPayload) {
        DefaultContractPaymentsKafkaProducer.log.info("sending ContractPaymentPayload='{}' to topic='{}'", contractPaymentPayload, topicsConfig.getTopicName());
        Message<ContractPaymentPayload> message = MessageBuilder
                .withPayload(contractPaymentPayload)
                .setHeader(KafkaHeaders.TOPIC, topicsConfig.getTopicName())
                .build();

        paymentsKafkaTemplate.send(message);
    }
}

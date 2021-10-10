package com.vosouq.commons.config.kafka.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "kafka.contractPaymentsTopic.enabled")
public class ContractPaymentsKafkaTopicsConfig {

    // contracts topic config
    @Value(value = "${kafka.contractPaymentsTopic.name:contractPaymentsTopic}")
    private String contractPaymentsTopic;
    @Value(value = "${kafka.contractPaymentsTopic.numPartitions:1}")
    private int contractPaymentsNumPartitions;
    @Value(value = "${kafka.contractPaymentsTopic.replicationFactor:1}")
    private short contractPaymentsReplicationFactor;

    @Bean
    public NewTopic contractPaymentsTopic() {
        return new NewTopic(contractPaymentsTopic, contractPaymentsNumPartitions, contractPaymentsReplicationFactor);
    }

    public String getTopicName() {
        return contractPaymentsTopic;
    }
}
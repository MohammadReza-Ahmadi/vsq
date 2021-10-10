package com.vosouq.commons.config.kafka.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "kafka.contractsTopic.enabled")
public class ContractsKafkaTopicsConfig {

    // contracts topic config
    @Value(value = "${kafka.contractsTopic.name:contractsTopic}")
    private String contractsTopic;
    @Value(value = "${kafka.contractsTopic.numPartitions:1}")
    private int contractsNumPartitions;
    @Value(value = "${kafka.contractsTopic.replicationFactor:1}")
    private short contractsReplicationFactor;

    @Bean
    public NewTopic contractsTopic() {
        return new NewTopic(contractsTopic, contractsNumPartitions, contractsReplicationFactor);
    }

    public String getTopicName() {
        return contractsTopic;
    }
}
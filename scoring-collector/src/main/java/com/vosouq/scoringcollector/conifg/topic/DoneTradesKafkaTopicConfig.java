package com.vosouq.scoringcollector.conifg.topic;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(value = "kafka.doneTradesTopic.enabled")
public class DoneTradesKafkaTopicConfig {

    // doneTrades topic config
    @Value(value = "${kafka.doneTradesTopic.name}")
    private String doneTradesTopic;
    @Value(value = "${kafka.doneTradesTopic.numPartitions}")
    private int numPartitions;
    @Value(value = "${kafka.doneTradesTopic.replicationFactor}")
    private short replicationFactor;

    private Map<String, Object> topicHeader;

    @Bean
    public NewTopic doneTradesTopic() {
        topicHeader = new HashMap<>();
        topicHeader.put(KafkaHeaders.TOPIC, doneTradesTopic);
        return new NewTopic(doneTradesTopic, numPartitions, replicationFactor);
    }

    public String getTopicName() {
        return doneTradesTopic;
    }

    public Map<String, Object> getTopicHeader() {
        return topicHeader;
    }
}
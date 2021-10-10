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
@ConditionalOnProperty(value = "kafka.undoneTradesTopic.enabled")
public class UndoneTradesKafkaTopicConfig {

    // doneTrades topic config
    @Value(value = "${kafka.undoneTradesTopic.name}")
    private String undoneTradesTopic;
    @Value(value = "${kafka.undoneTradesTopic.numPartitions}")
    private int numPartitions;
    @Value(value = "${kafka.undoneTradesTopic.replicationFactor}")
    private short replicationFactor;

    private Map<String, Object> topicHeader;

    @Bean
    public NewTopic undoneTradesTopic() {
        topicHeader = new HashMap<>();
        topicHeader.put(KafkaHeaders.TOPIC, undoneTradesTopic);
        return new NewTopic(undoneTradesTopic, numPartitions, replicationFactor);
    }

    public String getTopicName() {
        return undoneTradesTopic;
    }

    public Map<String, Object> getTopicHeader() {
        return topicHeader;
    }
}
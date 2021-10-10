package com.vosouq.scoringcollector.conifg.consumer;

//import com.fasterxml.jackson.databind.JsonDeserializer;

import com.vosouq.commons.model.kafka.ContractPayload;
import com.vosouq.commons.model.kafka.ContractPaymentPayload;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@ConditionalOnProperty(value = "kafka.contractPaymentsTopic.enabled")
public class ContractPaymentsKafkaConsumerConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${kafka.groups.contractPayments.id:contractPaymentsGroup}")
    private String contractPaymentsGroupId;


    // ContractPaymentPayload ConcurrentKafkaListenerContainerFactory
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ContractPaymentPayload> contractPaymentsKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ContractPaymentPayload> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(contractPaymentsConsumerFactory());
        factory.getContainerProperties().setMissingTopicsFatal(false);
        return factory;
    }

    // ContractPaymentPayload ConsumerFactory
    @SuppressWarnings("DuplicatedCode")
    public ConsumerFactory<String, ContractPaymentPayload> contractPaymentsConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, contractPaymentsGroupId);
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(ContractPaymentPayload.class));
    }
}
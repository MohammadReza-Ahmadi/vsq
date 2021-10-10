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
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@ConditionalOnProperty(value = "kafka.contractsTopic.enabled")
public class ContractsKafkaConsumerConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${kafka.groups.contracts.id:contractsGroup}")
    private String contractsGroupId;

    // ContractPayload ConcurrentKafkaListenerContainerFactory
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ContractPayload> contractsKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ContractPayload> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(contractsConsumerFactory());
        factory.getContainerProperties().setMissingTopicsFatal(false);
        return factory;
    }

    // ContractPayload ConsumerFactory
    @SuppressWarnings("DuplicatedCode")
    public ConsumerFactory<String, ContractPayload> contractsConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, contractsGroupId);
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(ContractPayload.class));
    }
}
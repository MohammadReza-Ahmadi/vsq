package com.vosouq.commons.config.kafka.producer;

import com.vosouq.commons.model.kafka.ContractPayload;
import com.vosouq.commons.model.kafka.ContractPaymentPayload;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"SpringFacetCodeInspection"})
@Configuration
@ConditionalOnProperty(value = "kafka.contractsTopic.enabled")
public class ContractsKafkaProducerConfig {

    @Value(value = "${kafka.bootstrapAddress:}")
    private String bootstrapAddress;

    private static Map<String, Object> getConfigProps(String bootstrapAddress) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return configProps;
    }

    @Bean
    public ProducerFactory<String, ContractPayload> contractsProducerFactory() {
        Map<String, Object> configProps = getConfigProps(bootstrapAddress);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, ContractPayload> contractsKafkaTemplate() {
        return new KafkaTemplate<>(contractsProducerFactory());
    }
}

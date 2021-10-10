package com.vosouq.config;

import com.vosouq.commons.service.producer.ContractPaymentsKafkaProducer;
import com.vosouq.commons.service.producer.ContractsKafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
@Slf4j
public class ContractKafkaConfig {
    @Bean
    public ContractsKafkaProducer contractsKafkaProducer() {
        return contractPayload -> {
            //noop
        };
    }

    @Bean
    public ContractPaymentsKafkaProducer contractPaymentsKafkaProducer() {
        return contractPaymentPayload -> {
            //noop
        };
    }
}

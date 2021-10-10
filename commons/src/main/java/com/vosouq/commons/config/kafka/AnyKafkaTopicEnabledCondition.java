package com.vosouq.commons.config.kafka;

import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

public class AnyKafkaTopicEnabledCondition extends AnyNestedCondition {

    public AnyKafkaTopicEnabledCondition() {
        super(ConfigurationPhase.PARSE_CONFIGURATION);
    }

    @ConditionalOnProperty(value = "kafka.contractsTopic.enabled")
    static class Value1Condition {}

    @ConditionalOnProperty(value = "kafka.contractPaymentsTopic.enabled")
    static class Value2Condition {}

    @ConditionalOnProperty(value = "kafka.messagesTopic.enabled")
    static class Value3Condition {}

}
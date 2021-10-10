package com.vosouq.scoringcollector.service.producer;

import com.vosouq.scoringcollector.conifg.topic.UndoneTradesKafkaTopicConfig;
import com.vosouq.scoringcollector.model.payload.producing.DoneTradePayload;
import com.vosouq.scoringcollector.model.payload.producing.UndoneTradePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "kafka.undoneTradesTopic.enabled")
public class UndoneTradesKafkaProducer {
    private static final Logger LOG = LoggerFactory.getLogger(UndoneTradesKafkaProducer.class);

    private final UndoneTradesKafkaTopicConfig topicConfig;
    private final KafkaTemplate<String, UndoneTradePayload> undoneTradesKafkaTemplate;

    public UndoneTradesKafkaProducer(UndoneTradesKafkaTopicConfig topicConfig, KafkaTemplate<String, UndoneTradePayload> undoneTradesKafkaTemplate) {
        this.topicConfig = topicConfig;
        this.undoneTradesKafkaTemplate = undoneTradesKafkaTemplate;
    }

    public void send(UndoneTradePayload undoneTradePayload) {
        LOG.info("sending UndoneTradePayload='{}' to topic='{}'", undoneTradePayload, topicConfig.getTopicName());
        Message<UndoneTradePayload> message = MessageBuilder
                .withPayload(undoneTradePayload)
                .setHeader(KafkaHeaders.TOPIC, topicConfig.getTopicName())
                .build();

        undoneTradesKafkaTemplate.send(message);
    }
}

package com.vosouq.scoringcollector.service.producer;

import com.vosouq.scoringcollector.conifg.topic.DoneTradesKafkaTopicConfig;
import com.vosouq.scoringcollector.model.payload.producing.DoneTradePayload;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(value = "kafka.doneTradesTopic.enabled")
public class DoneTradesKafkaProducer {

    private final DoneTradesKafkaTopicConfig topicConfig;
    private final KafkaTemplate<String, DoneTradePayload> doneTradesKafkaTemplate;

    public DoneTradesKafkaProducer(DoneTradesKafkaTopicConfig topicConfig, KafkaTemplate<String, DoneTradePayload> doneTradesKafkaTemplate) {
        this.topicConfig = topicConfig;
        this.doneTradesKafkaTemplate = doneTradesKafkaTemplate;
    }

    public void send(DoneTradePayload doneTradePayload) {
        log.info("sending DoneTradePayload='{}' to topic='{}'", doneTradePayload, topicConfig.getTopicName());
//        doneTradesKafkaTemplate.send(new GenericMessage<DoneTrade>(doneTrade, topicConfig.getTopicHeader()));
//        doneTradesKafkaTemplate.send(topicConfig.getTopicName(),doneTrade);
        Message<DoneTradePayload> message = MessageBuilder
                .withPayload(doneTradePayload)
                .setHeader(KafkaHeaders.TOPIC, topicConfig.getTopicName())
                .build();

        doneTradesKafkaTemplate.send(message);
    }
}

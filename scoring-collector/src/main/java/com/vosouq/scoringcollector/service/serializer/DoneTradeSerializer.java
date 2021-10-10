package com.vosouq.scoringcollector.service.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vosouq.scoringcollector.model.payload.producing.DoneTradePayload;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class DoneTradeSerializer implements Serializer<DoneTradePayload> {

    @Override
    public byte[] serialize(String s, DoneTradePayload doneTradePayload) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(doneTradePayload).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, Headers headers, DoneTradePayload doneTradePayload) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(doneTradePayload).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }

    @Override
    public void close() {

    }
}

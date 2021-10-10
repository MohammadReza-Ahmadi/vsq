package com.vosouq.scoringcollector.service.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vosouq.scoringcollector.model.payload.producing.DoneTradePayload;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class DoneTradeDeserializer implements Deserializer<DoneTradePayload> {

    @Override
    public DoneTradePayload deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        DoneTradePayload doneTradePayload = null;
        try {
            doneTradePayload = mapper.readValue(bytes, DoneTradePayload.class);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return doneTradePayload;
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public DoneTradePayload deserialize(String topic, Headers headers, byte[] data) {
        ObjectMapper mapper = new ObjectMapper();
        DoneTradePayload doneTradePayload = null;
        try {
            doneTradePayload = mapper.readValue(data, DoneTradePayload.class);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return doneTradePayload;
    }

    @Override
    public void close() {

    }
}

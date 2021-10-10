package com.vosouq.scoringcollector.controller;

import com.vosouq.commons.model.kafka.ContractPayload;
import com.vosouq.commons.model.kafka.ContractPaymentPayload;
import com.vosouq.commons.service.producer.impl.DefaultContractPaymentsKafkaProducer;
import com.vosouq.commons.service.producer.impl.DefaultContractsKafkaProducer;
import com.vosouq.scoringcollector.model.payload.producing.DoneTradePayload;
import com.vosouq.scoringcollector.service.producer.DoneTradesKafkaProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/scoring-collector")
@ConditionalOnProperty(value = "kafka.contractsTopic.enabled")
public class CollectorController {

    private DoneTradesKafkaProducer doneTradesProducer;
    private DefaultContractsKafkaProducer contractsProducer;
    private DefaultContractPaymentsKafkaProducer contractPaymentsProducer;

    public CollectorController(DoneTradesKafkaProducer doneTradesProducer, DefaultContractsKafkaProducer contractsProducer, DefaultContractPaymentsKafkaProducer contractPaymentsProducer) {
        this.doneTradesProducer = doneTradesProducer;
        this.contractsProducer = contractsProducer;
        this.contractPaymentsProducer = contractPaymentsProducer;
    }


    // --------- simulate consumer part of scoring collector to receive contract microservice payload messages -----------
    //this is a test call for simulating contractPayload message
    @PostMapping(value = "/contracts")
    public void sendContractPayload(@RequestBody ContractPayload contractPayload) {
        contractsProducer.send(contractPayload);
    }

    //this is a test call for simulating contractPaymentPayload message
    @PostMapping(value = "/contract-payments")
    public void sendContractPaymentPayload(@RequestBody ContractPaymentPayload contractPaymentPayload) {
        contractPaymentsProducer.send(contractPaymentPayload);
    }


    // --------- simulate producer part of scoring collector to send done and undone trades payload messages0 for scoring-calculator microservice -----------
    //this is a test call for simulating DoneTradePayload message
    @PostMapping(value = "/doneTrades")
    public void sendDoneTrade(@RequestBody DoneTradePayload doneTradePayload) {
        doneTradesProducer.send(doneTradePayload);
    }

}

package com.vosouq.scoringcollector.service.handler.ready.impl;

import com.vosouq.commons.model.kafka.ContractPayload;
import com.vosouq.scoringcollector.model.UserRole;
import com.vosouq.scoringcollector.model.hist.UndoneTradeHist;
import com.vosouq.scoringcollector.model.payload.producing.UndoneTrade;
import com.vosouq.scoringcollector.model.payload.producing.UndoneTradePayload;
import com.vosouq.scoringcollector.repository.UndoneTradeHistRepository;
import com.vosouq.scoringcollector.repository.UndoneTradeRepository;
import com.vosouq.scoringcollector.service.handler.ready.UndoneTradeReadyDataHandler;
import com.vosouq.scoringcollector.service.producer.UndoneTradesKafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import static com.vosouq.scoringcollector.util.ScoringCollectorConstant.ONE_INT;
import static java.util.Objects.isNull;

@Slf4j
@Service
@Transactional
public class UndoneTradeReadyDataKafkaHandler implements UndoneTradeReadyDataHandler {

    private UndoneTradeRepository undoneTradeRepository;
    private UndoneTradeHistRepository undoneTradeHistRepository;
    private UndoneTradesKafkaProducer undoneTradesKafkaProducer;

    public UndoneTradeReadyDataKafkaHandler(UndoneTradeRepository undoneTradeRepository, UndoneTradeHistRepository undoneTradeHistRepository, UndoneTradesKafkaProducer undoneTradesKafkaProducer) {
        this.undoneTradeRepository = undoneTradeRepository;
        this.undoneTradeHistRepository = undoneTradeHistRepository;
        this.undoneTradesKafkaProducer = undoneTradesKafkaProducer;
    }

    @Override
    public void handleContractCreateEvent(ContractPayload event) {
        // load seller undoneTrade
        UndoneTrade sellerUndoneTrade = undoneTradeRepository.findByUserId(event.getSellerId());
        // save or update seller undoneTrade
        sellerUndoneTrade = createOrUpdateUndoneTrade(event, sellerUndoneTrade, UserRole.SELLER, event.getSellerId());
        // send undoneTrade event for seller to undoneTradesTopic
        makeUndoneTradePayloadAndSendToKafka(sellerUndoneTrade, new UndoneTradePayload());

        // load buyer undoneTrade
        UndoneTrade buyerUndoneTrade = undoneTradeRepository.findByUserId(event.getBuyerId());
        // save or update buyer undoneTrade
        buyerUndoneTrade = createOrUpdateUndoneTrade(event, buyerUndoneTrade, UserRole.BUYER, event.getBuyerId());
        // send undoneTrade event for buyer to undoneTradesTopic
        makeUndoneTradePayloadAndSendToKafka(buyerUndoneTrade, new UndoneTradePayload());
    }

    private void makeUndoneTradePayloadAndSendToKafka(UndoneTrade buyerUndoneTrade, UndoneTradePayload undoneTradePayload) {
        try {
            BeanUtils.copyProperties(undoneTradePayload, buyerUndoneTrade);
            undoneTradesKafkaProducer.send(undoneTradePayload);

        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private UndoneTrade createOrUpdateUndoneTrade(ContractPayload event, UndoneTrade undoneTrade, UserRole userRole, Long userId) {
        // make UndoneTrade
        if (isNull(undoneTrade)) {
            // create seller undoneTrade
            undoneTrade = UndoneTrade.newInstance();
            undoneTrade.setUserId(userId);
            undoneTrade.setUndueTradesCount(ONE_INT);
            undoneTrade.setUndueTradesTotalBalanceOfLastYear(BigDecimal.valueOf(event.getAmount()));
        } else {
            // update seller undoneTrade
            undoneTrade.increaseUndueTradesCount();
            undoneTrade.increaseUndueTradesTotalBalanceOfLastYear(BigDecimal.valueOf(event.getAmount()));
        }
        // save UndoneTrade
        undoneTradeRepository.save(undoneTrade);

        // make UndoneTradeHist
        UndoneTradeHist undoneTradeHist = new UndoneTradeHist();
        try {
            BeanUtils.copyProperties(undoneTradeHist, undoneTrade);
            undoneTradeHist.setId(null);
            undoneTradeHist.setContractId(event.getContractId());
            undoneTradeHist.setUserRole(userRole.getCode());

        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // save UndoneTradeHist
        undoneTradeHistRepository.insert(undoneTradeHist);
        return undoneTrade;
    }
}

package com.vosouq.scoringcollector.service.handler.ready.impl;

import com.vosouq.commons.model.kafka.ContractPayload;
import com.vosouq.scoringcollector.model.UserRole;
import com.vosouq.scoringcollector.model.hist.DoneTradeHist;
import com.vosouq.scoringcollector.model.payload.producing.DoneTrade;
import com.vosouq.scoringcollector.model.payload.producing.DoneTradePayload;
import com.vosouq.scoringcollector.repository.DoneTradeHistRepository;
import com.vosouq.scoringcollector.repository.DoneTradeRepository;
import com.vosouq.scoringcollector.service.handler.ready.DoneTradeReadyDataHandler;
import com.vosouq.scoringcollector.service.producer.DoneTradesKafkaProducer;
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
public class DoneTradeReadyDataKafkaHandler implements DoneTradeReadyDataHandler {

    private DoneTradeRepository doneTradeRepository;
    private DoneTradeHistRepository doneTradeHistRepository;
    private DoneTradesKafkaProducer doneTradesKafkaProducer;

    public DoneTradeReadyDataKafkaHandler(DoneTradeRepository doneTradeRepository, DoneTradeHistRepository doneTradeHistRepository, DoneTradesKafkaProducer doneTradesKafkaProducer) {
        this.doneTradeRepository = doneTradeRepository;
        this.doneTradeHistRepository = doneTradeHistRepository;
        this.doneTradesKafkaProducer = doneTradesKafkaProducer;
    }


    @Override
    public void handleContractDoneEvent(ContractPayload event) {
        // load seller undoneTrade
        DoneTrade sellerDoneTrade = doneTradeRepository.findByUserId(event.getSellerId());
        // save or update seller undoneTrade
        sellerDoneTrade = createOrUpdateDoneTrade(event, sellerDoneTrade, UserRole.SELLER, event.getSellerId());
        // send undoneTrade event for seller to undoneTradesTopic
        makeUndoneTradePayloadAndSendToKafka(sellerDoneTrade, new DoneTradePayload());

        // load buyer undoneTrade
        DoneTrade buyerDoneTrade = doneTradeRepository.findByUserId(event.getBuyerId());
        // save or update buyer undoneTrade
        buyerDoneTrade = createOrUpdateDoneTrade(event, buyerDoneTrade, UserRole.BUYER, event.getBuyerId());
        // send undoneTrade event for buyer to undoneTradesTopic
        makeUndoneTradePayloadAndSendToKafka(buyerDoneTrade, new DoneTradePayload());
    }

    private void makeUndoneTradePayloadAndSendToKafka(DoneTrade buyerDoneTrade, DoneTradePayload doneTradePayload) {
        try {
            BeanUtils.copyProperties(doneTradePayload, buyerDoneTrade);
            doneTradesKafkaProducer.send(doneTradePayload);

        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private DoneTrade createOrUpdateDoneTrade(ContractPayload event, DoneTrade doneTrade, UserRole userRole, Long userId) {
        // make UndoneTrade
        if (isNull(doneTrade)) {
            // create seller undoneTrade
            doneTrade = DoneTrade.newInstance();
            doneTrade.setUserId(userId);
            doneTrade.setTimelyTradesCountOfLast3Months(ONE_INT);
            doneTrade.setTradesTotalBalance(BigDecimal.valueOf(event.getAmount()));
        } else {
            // update seller undoneTrade
            doneTrade.increaseTimelyTradesCountOfLast3Months();
            doneTrade.increaseTradesTotalBalance(BigDecimal.valueOf(event.getAmount()));
        }
        // save UndoneTrade
        doneTradeRepository.save(doneTrade);

        // make UndoneTradeHist
        DoneTradeHist doneTradeHist = new DoneTradeHist();
        try {
            BeanUtils.copyProperties(doneTradeHist, doneTrade);
            doneTradeHist.setId(null);
            doneTradeHist.setContractId(event.getContractId());
            doneTradeHist.setUserRole(userRole.getCode());

        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // save UndoneTradeHist
        doneTradeHistRepository.insert(doneTradeHist);
        return doneTrade;
    }
}

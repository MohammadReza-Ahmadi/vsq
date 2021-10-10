package com.vosouq.scoringcollector.model.payload.producing;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

import static com.vosouq.scoringcollector.util.ScoringCollectorConstant.ONE_INT;
import static com.vosouq.scoringcollector.util.ScoringCollectorConstant.ZERO_INT;

@Getter
@Setter
@Document(collection = "undoneTrades")
public class UndoneTrade extends UndoneTradePayload {
    @Id
    private String id;

    private Date calculationStartDate;

    public static UndoneTrade newInstance() {
        UndoneTrade undoneTrade = new UndoneTrade();
        undoneTrade.setCalculationStartDate(new Date());
        undoneTrade.setPastDueTradesCount(ZERO_INT);
        undoneTrade.setArrearTradesCount(ZERO_INT);
        undoneTrade.setPastDueTradesTotalBalanceOfLastYear(BigDecimal.ZERO);
        undoneTrade.setArrearTradesTotalBalanceOfLastYear(BigDecimal.ZERO);
        return undoneTrade;
    }

    public void increaseUndueTradesCount() {
        setUndueTradesCount(getUndueTradesCount() + ONE_INT);
    }

    public void increaseUndueTradesTotalBalanceOfLastYear(BigDecimal amount) {
        setUndueTradesTotalBalanceOfLastYear(getUndueTradesTotalBalanceOfLastYear().add(amount));
    }

}

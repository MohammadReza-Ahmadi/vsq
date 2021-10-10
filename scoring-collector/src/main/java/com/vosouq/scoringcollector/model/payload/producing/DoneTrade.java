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
@Document(collection = "doneTrades")
public class DoneTrade extends DoneTradePayload {
    @Id
    private String id;

    @JsonProperty("calculation_start_date")
    private Date calculationStartDate;

    public static DoneTrade newInstance() {
        DoneTrade doneTrade = new DoneTrade();
        doneTrade.setCalculationStartDate(new Date());
        doneTrade.setPastDueTradesCountOfLast3Months(ZERO_INT);
        doneTrade.setArrearTradesCountOfLast3Months(ZERO_INT);
        doneTrade.setTimelyTradesCountBetweenLast3To12Months(ZERO_INT);
        doneTrade.setPastDueTradesCountBetweenLast3To12Months(ZERO_INT);
        doneTrade.setArrearTradesCountBetweenLast3To12Months(ZERO_INT);
        doneTrade.setTotalDelayDays(ZERO_INT);
        return doneTrade;
    }
}

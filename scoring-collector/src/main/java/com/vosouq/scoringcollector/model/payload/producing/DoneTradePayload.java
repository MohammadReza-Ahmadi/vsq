package com.vosouq.scoringcollector.model.payload.producing;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

import static com.vosouq.scoringcollector.util.ScoringCollectorConstant.ONE_INT;

@ToString
@Getter
@Setter
public class DoneTradePayload {
    @JsonProperty("user_id")
    private Long userId;

    // count fields
    @JsonProperty("timely_trades_count_of_last_3_months")
    private Integer timelyTradesCountOfLast3Months;
    @JsonProperty("past_due_trades_count_of_last_3_months")
    private Integer pastDueTradesCountOfLast3Months;
    @JsonProperty("arrear_trades_count_of_last_3_months")
    private Integer arrearTradesCountOfLast3Months;
    @JsonProperty("timely_trades_count_between_last_3_to_12_months")
    private Integer timelyTradesCountBetweenLast3To12Months;
    @JsonProperty("past_due_trades_count_between_last_3_to_12_months")
    private Integer pastDueTradesCountBetweenLast3To12Months;
    @JsonProperty("arrear_trades_count_between_last_3_to_12_months")
    private Integer arrearTradesCountBetweenLast3To12Months;
    @JsonProperty("total_delay_days")
    private Integer totalDelayDays;

    // amount fields
    @JsonProperty("trades_total_balance")
    private BigDecimal tradesTotalBalance;


    public void increaseTimelyTradesCountOfLast3Months() {
        setTimelyTradesCountOfLast3Months(getTimelyTradesCountOfLast3Months() + ONE_INT);
    }

    public void increaseTradesTotalBalance(BigDecimal amount) {
        setTradesTotalBalance(getTradesTotalBalance().add(amount));
    }
}

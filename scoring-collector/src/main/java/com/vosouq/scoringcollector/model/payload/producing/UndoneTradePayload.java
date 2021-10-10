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
public class UndoneTradePayload {
    @JsonProperty("user_id")
    private Long userId;

    // count fields
    @JsonProperty("undue_trades_count")
    private Integer undueTradesCount;
    @JsonProperty("past_due_trades_count")
    private Integer pastDueTradesCount;
    @JsonProperty("arrear_trades_count")
    private Integer arrearTradesCount;

    // balance fields
    @JsonProperty("undue_trades_total_balance_of_last_year")
    private BigDecimal undueTradesTotalBalanceOfLastYear;
    @JsonProperty("past_due_trades_total_balance_of_last_year")
    private BigDecimal pastDueTradesTotalBalanceOfLastYear;
    @JsonProperty("arrear_trades_total_balance_of_last_year")
    private BigDecimal arrearTradesTotalBalanceOfLastYear;
}

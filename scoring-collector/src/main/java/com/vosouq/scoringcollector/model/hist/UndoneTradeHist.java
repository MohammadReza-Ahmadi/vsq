package com.vosouq.scoringcollector.model.hist;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vosouq.scoringcollector.model.payload.producing.UndoneTradePayload;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@Document(collection = "undoneTradesHist")
public class UndoneTradeHist extends UndoneTradePayload {
    @Id
    private String id;

    private Long contractId;

    private Character userRole;

    private Date calculationStartDate;
}

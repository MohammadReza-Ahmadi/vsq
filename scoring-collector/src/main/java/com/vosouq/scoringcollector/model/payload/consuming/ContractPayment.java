package com.vosouq.scoringcollector.model.payload.consuming;

import com.vosouq.commons.model.kafka.ContractPaymentPayload;
import com.vosouq.commons.model.kafka.PaymentPayloadStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document(collection = "contractPayments")
public class ContractPayment extends ContractPaymentPayload {
    @Id
    private String id;
}

package com.vosouq.scoringcollector.model.payload.consuming;

import com.vosouq.commons.model.kafka.ContractPayload;
import com.vosouq.commons.model.kafka.ContractPaymentPayload;
import com.vosouq.commons.model.kafka.ContractProgressStatus;
import com.vosouq.commons.model.kafka.ObligatedParty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document(collection = "contracts")
public class Contract extends ContractPayload {
    @Id
    private String id;
    private List<Long> paymentIds;

    @Override
    @Transient
    public void setPayments(List<ContractPaymentPayload> payments) {
        super.setPayments(payments);
    }
}

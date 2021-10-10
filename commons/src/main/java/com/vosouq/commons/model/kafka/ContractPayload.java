package com.vosouq.commons.model.kafka;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ContractPayload {
    private Long contractId;
    private Long sellerId;
    private Long buyerId;
    private Long amount;
    private Date startDate;
    private ContractProgressStatus dueStatus;
    private ObligatedParty obligatedParty;
    private List<ContractPaymentPayload> payments;
}

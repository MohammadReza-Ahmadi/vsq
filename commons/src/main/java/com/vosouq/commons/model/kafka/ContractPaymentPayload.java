package com.vosouq.commons.model.kafka;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Getter
@Setter
public class ContractPaymentPayload {
    private Long contractId;
    private Long paymentId;
    private Long amount;
    private Date dueDate;
    private Date createDate;
    private Date updateDate;
    private Date paymentDate;
    private Boolean isDelayed;
    private PaymentPayloadStatus status;
}


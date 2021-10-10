package com.vosouq.contract.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractStatusModel {

    private Long contractId;
    private Long sellerId;
    private Long buyerId;
    private Timestamp now;
    private Timestamp currentActionDueDate;
    private Action action;
    private String[] messages;
    private String timerMessage;
    private int paymentsCount;

    private List<ContractStepModel> steps;
}
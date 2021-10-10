package com.vosouq.contract.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContractStepModel {

    private String title;
    private ContractState state;
    private Actor activeActor;
    private Actor left;
    private Actor right;
    private Long amount;
    private Timestamp dueDate;
    private Timestamp actionDate;
    private ContractStepStatus stepStatus;
}
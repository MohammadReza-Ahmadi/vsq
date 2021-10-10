package com.vosouq.contract.controller.dto;

import com.vosouq.contract.model.Action;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ContractStatusResponse {
    private Long contractId;
    private Long now;
    private Long currentActionDueDate;
    private Action action;
    private String[] messages;
    private String timerMessage;

    private List<ContractStepResponse> steps;
}
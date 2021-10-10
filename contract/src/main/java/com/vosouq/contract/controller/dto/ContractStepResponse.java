package com.vosouq.contract.controller.dto;

import com.vosouq.contract.model.ContractStepStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractStepResponse {

    private String title;
    private ActiveActor activeActor;
    private Long amount;
    private String leftImage;
    private String rightImage;
    private Long actionDate;
    private Long dueDate;
    private ContractStepStatus stepStatus;
}
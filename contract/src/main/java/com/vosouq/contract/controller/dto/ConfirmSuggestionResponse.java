package com.vosouq.contract.controller.dto;

import com.vosouq.contract.model.ContractState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmSuggestionResponse {

    private Long contractId;
    private ContractState state;

}
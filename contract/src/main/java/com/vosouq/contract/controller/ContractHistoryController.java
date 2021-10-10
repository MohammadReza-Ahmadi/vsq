package com.vosouq.contract.controller;

import com.vosouq.commons.annotation.VosouqRestController;
import com.vosouq.commons.model.OnlineUser;
import com.vosouq.contract.controller.dto.GetAllContractsResponse;
import com.vosouq.contract.controller.mapper.ContractMapper;
import com.vosouq.contract.model.ContractState;
import com.vosouq.contract.service.ContractService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@VosouqRestController
@RequestMapping("/history")
public class ContractHistoryController {

    private final ContractService contractService;
    private final OnlineUser onlineUser;

    public ContractHistoryController(ContractService contractService,
                                     @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
                                             OnlineUser onlineUser) {
        this.contractService = contractService;
        this.onlineUser = onlineUser;
    }

    @GetMapping
    public List<GetAllContractsResponse> getHistory(
            @RequestParam(required = false, defaultValue = "") String term,
            @RequestParam(required = false, defaultValue = "1") @Min(1) Integer page,
            @RequestParam(required = false, defaultValue = "25") @Max(200) Integer pageSize) {
        return contractService.findAllContractModels(onlineUser.getUserId(),
                term,
                List.of(ContractState.FEE_DEPOSIT_END,
                        ContractState.CONTRACT_END,
                        ContractState.DELIVERY_DONE,
                        ContractState.STALE,
                        ContractState.BUYER_APPROVAL
                ),
                page,
                pageSize)
                .stream()
                .map(ContractMapper::map)
                .collect(Collectors.toList());
    }
}

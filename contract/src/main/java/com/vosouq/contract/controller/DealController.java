package com.vosouq.contract.controller;

import com.vosouq.commons.annotation.VosouqRestController;
import com.vosouq.commons.model.OnlineUser;
import com.vosouq.contract.controller.dto.ActionStateFilterResponse;
import com.vosouq.contract.controller.dto.RecentDealResponse;
import com.vosouq.contract.model.ActionState;
import com.vosouq.contract.model.RecentDealModel;
import com.vosouq.contract.service.ContractService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

import static com.vosouq.contract.utills.ContractStateUtil.getActionHintString;

@VosouqRestController
public class DealController {

    private final ContractService contractService;
    private final OnlineUser onlineUser;

    public DealController(ContractService contractService, OnlineUser onlineUser) {
        this.contractService = contractService;
        this.onlineUser = onlineUser;
    }

    @GetMapping("/deals/recent")
    public List<RecentDealResponse> getAll(
            @RequestParam(required = false) List<ActionState> filters,
            @RequestParam(required = false, defaultValue = "1") @Min(1) Integer page,
            @RequestParam(required = false, defaultValue = "25") @Max(200) @Min(1) Integer pageSize) {

        List<RecentDealModel> recentDealModels;
        if(filters == null || filters.isEmpty()){
            recentDealModels = contractService.getRecentDeals(onlineUser.getUserId(), page, pageSize);
        } else {
            recentDealModels = contractService.getRecentDeals(onlineUser.getUserId(),
                    filters,
                    page,
                    pageSize);
        }

        return recentDealModels
                .stream()
                .map(recentDealModel ->
                        new RecentDealResponse(
                                recentDealModel.getId(),
                                recentDealModel.getTitle(),
                                recentDealModel.getAction(),
                                recentDealModel.getActionHint(),
                                recentDealModel.getState(),
                                recentDealModel.getDate(),
                                recentDealModel.getActionState()))
                .collect(Collectors.toList());
    }

    @GetMapping("/deals/filters")
    public List<ActionStateFilterResponse> getAvailableFilters() {
        return List.of(ActionState.values()).stream().map(actionState ->
                new ActionStateFilterResponse(actionState.toString(), getActionHintString(actionState)))
                .collect(Collectors.toList());
    }
}
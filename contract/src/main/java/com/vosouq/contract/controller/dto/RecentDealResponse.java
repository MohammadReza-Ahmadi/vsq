package com.vosouq.contract.controller.dto;

import com.vosouq.contract.model.ActionState;
import com.vosouq.contract.model.RecentDealModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecentDealResponse {

    private Long id;
    private String title;
    private String action;
    private String actionHint;
    private RecentDealModel.DealState state;
    private Long date;
    private ActionState actionState;

}

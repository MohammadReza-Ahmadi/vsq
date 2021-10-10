package com.vosouq.contract.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.vosouq.contract.utills.ContractStateUtil.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecentDealModel {

    private Long id;
    private String title;
    private String action;
    private String actionHint;
    private DealState state;
    private Long date;
    private ActionState actionState;

    public enum DealState {
        SUCCESS, PENDING, EXPIRED
    }

    public static RecentDealModel fromDealHistory(DealHistory dealHistory) {
        RecentDealModel recentDealModel = new RecentDealModel();
        recentDealModel.setId(dealHistory.getSubjectId());
        recentDealModel.setDate(dealHistory.getActionDate().getTime());
        recentDealModel.setTitle(dealHistory.getTitle());
        recentDealModel.setAction(getActionLabelString(dealHistory.getAction()));
        recentDealModel.setActionHint(getActionHintString(dealHistory.getActionState()));
        recentDealModel.setActionState(dealHistory.getActionState());
        recentDealModel.setState(getDealState(dealHistory.getActionState(),
                dealHistory.getActionDate().getTime()));

        return recentDealModel;
    }

    @Override
    public String toString() {
        return "RecentDealModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", action='" + action + '\'' +
                ", actionHint='" + actionHint + '\'' +
                ", state=" + state +
                ", date=" + date +
                ", actionState=" + actionState +
                '}';
    }
}

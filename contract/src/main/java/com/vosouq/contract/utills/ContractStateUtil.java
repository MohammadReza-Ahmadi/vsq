package com.vosouq.contract.utills;

import com.vosouq.commons.util.MessageUtil;
import com.vosouq.contract.model.Action;
import com.vosouq.contract.model.ActionState;
import com.vosouq.contract.model.RecentDealModel;

import java.util.Date;

public class ContractStateUtil {
    public static String getActionHintString(ActionState actionState) {
        switch (actionState) {
            case PAID:
                return MessageUtil.getMessage("ActionState.Paid");
            case DELIVERED:
            case END_OF_CONTRACT:
                return MessageUtil.getMessage("ActionState.Delivered");
            case WAITING_FOR_PAYMENT:
                return MessageUtil.getMessage("ActionState.WaitingForPayment");
            case WAITING_FOR_DELIVERY:
                return MessageUtil.getMessage("ActionState.WaitingForDelivery");
            case WAITING_FOR_SIGN:
                return MessageUtil.getMessage("ActionState.WaitingForSign");
            case SIGNED:
                return MessageUtil.getMessage("ActionState.Signed");
            default:
                return "";
        }
    }

    public static String getActionLabelString(Action action) {
        switch (action) {
            case FIRST_STEP_PAYMENT:
                return MessageUtil.getMessage("Action.FirstStepPayment");
            case SECOND_STEP_PAYMENT:
                return MessageUtil.getMessage("Action.SecondStepPayment");
            case THIRD_STEP_PAYMENT:
                return MessageUtil.getMessage("Action.ThirdStepPayment");
            case DELIVER:
                return MessageUtil.getMessage("Action.Deliver");
            case PAY:
                return MessageUtil.getMessage("Action.Pay");
            case SIGN_BY_SELLER:
            case SIGN_BY_BUYER:
                return MessageUtil.getMessage("Action.SignBySeller");
            default:
                return "";
        }
    }

    public static RecentDealModel.DealState getDealState(ActionState actionState, Long date) {
        switch (actionState) {
            case WAITING_FOR_DELIVERY:
            case WAITING_FOR_SIGN:
            case WAITING_FOR_PAYMENT: {
                if (date.compareTo((new Date()).getTime()) <= 0)
                    return RecentDealModel.DealState.EXPIRED;
                else
                    return RecentDealModel.DealState.PENDING;
            }
            case END_OF_CONTRACT:
            case DELIVERED:
            case PAID:
            case SIGNED:
                return RecentDealModel.DealState.SUCCESS;
        }

        return null;
    }
}

package com.vosouq.util;

import com.vosouq.contract.model.Action;
import com.vosouq.contract.model.ActionState;
import com.vosouq.contract.model.Actor;
import com.vosouq.contract.model.DealHistory;

import java.sql.Timestamp;
import static com.vosouq.util.RandomGeneratorUtility.*;

public class DealHistoryTestUtil {
    public static DealHistory createDealHistory(long buyerId, long sellerId){
        DealHistory dealHistory = new DealHistory();
        dealHistory.setAction(randomEnumOf(Action.class));
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - generateRandomNumber(
                1000 * 60 * 60,
                1000 * 60 * 60 * 24));
        dealHistory.setActionDate(timestamp);
        dealHistory.setActionState(randomEnumOf(ActionState.class));
        dealHistory.setActor(randomEnumOf(Actor.class));
        dealHistory.setBuyerId(buyerId);
        dealHistory.setSellerId(sellerId);
        dealHistory.setCreateDate(timestamp);
        dealHistory.setTitle(generateSynthesizedName(8));
        dealHistory.setUpdateDate(timestamp);
        return dealHistory;
    }
}

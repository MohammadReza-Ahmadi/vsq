package com.vosouq.paymentgateway.mellat.service.builder;

import com.vosouq.paymentgateway.mellat.model.ipg.domainobject.IpgCallbackParameters;

public class IpgCallbackParametersBuilder {

    public static IpgCallbackParameters build(String refId,
                                              String resCod,
                                              Long saleOrderId,
                                              Long saleReferenceId,
                                              Long finalAmount,
                                              String cardHoldPA,
                                              String creditCardSaleResponseDetail) {
        IpgCallbackParameters instance = new IpgCallbackParameters();
        instance.setRefId(refId);
        instance.setResCod(resCod);
        instance.setSaleOrderId(saleOrderId);
        instance.setSaleReferenceId(saleReferenceId);
        instance.setFinalAmount(finalAmount);
        instance.setCardHoldPA(cardHoldPA);
        instance.setCreditCardSaleResponseDetail(creditCardSaleResponseDetail);
        return instance;
    }
}

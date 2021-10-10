package com.vosouq.paymentgateway.mellat.model.ipg.domainobject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IpgCallbackParameters {
    private String refId;
    private String resCod;
    private Long saleOrderId;
    private Long saleReferenceId;
    private Long finalAmount;
    private String cardHoldPA;
    private String creditCardSaleResponseDetail;
}

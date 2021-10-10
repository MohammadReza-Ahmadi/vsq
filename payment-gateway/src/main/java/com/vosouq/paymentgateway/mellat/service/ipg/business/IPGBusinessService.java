package com.vosouq.paymentgateway.mellat.service.ipg.business;

import com.vosouq.paymentgateway.mellat.controller.dto.GetIpgPaymentRefIdRequest;
import com.vosouq.paymentgateway.mellat.model.ipg.domainobject.IpgCallbackParameters;
import com.vosouq.paymentgateway.mellat.model.ipg.domainobject.IpgResponse;
import com.vosouq.paymentgateway.mellat.model.ipg.domainobject.IpgPaymentResponse;


public interface IPGBusinessService {

    IpgResponse getIpgPaymentRefId(GetIpgPaymentRefIdRequest getIpgPaymentRefIdRequest);

    IpgPaymentResponse handleIpgPaymentResponse(IpgCallbackParameters ipgCallbackParameters);
}

package com.vosouq.paymentgateway.mellat.webservice.ipg;

import com.vosouq.paymentgateway.mellat.model.ipg.BpPayment;
import com.vosouq.paymentgateway.mellat.model.ipg.domainobject.IpgResponse;
import com.vosouq.paymentgateway.mellat.webservice.ipg.gen.BpPayRequest;
import com.vosouq.paymentgateway.mellat.webservice.ipg.gen.BpSettleRequest;
import com.vosouq.paymentgateway.mellat.webservice.ipg.gen.BpVerifyRequest;


public interface IPGWebService {

    IpgResponse bpPayRequest(BpPayRequest bpPayRequest);

    IpgResponse bpVerifyRequest(BpVerifyRequest bpVerifyRequest);

    IpgResponse bpSettleRequest(BpSettleRequest bpSettleRequest);
}

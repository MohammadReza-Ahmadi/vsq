package com.vosouq.paymentgateway.mellat.webservice.ipg.impl;

import com.vosouq.paymentgateway.mellat.constant.CommonConstants;
import com.vosouq.paymentgateway.mellat.constant.NumberConstants;
import com.vosouq.paymentgateway.mellat.enumeration.BpResponseCode;
import com.vosouq.paymentgateway.mellat.exception.ipg.IpgMellatInvalidDataFormatException;
import com.vosouq.paymentgateway.mellat.exception.ipg.IpgMellatInvalidResponseException;
import com.vosouq.paymentgateway.mellat.model.ipg.domainobject.IpgResponse;
import com.vosouq.paymentgateway.mellat.util.AppUtil;
import com.vosouq.paymentgateway.mellat.webservice.ipg.IPGSoapConnector;
import com.vosouq.paymentgateway.mellat.webservice.ipg.IPGWebService;
import com.vosouq.paymentgateway.mellat.webservice.ipg.gen.*;
import org.springframework.stereotype.Service;

@Service
public class IPGWebServiceImpl implements IPGWebService {

    private final IPGSoapConnector ipgSoapConnector;

    public IPGWebServiceImpl(IPGSoapConnector ipgSoapConnector) {
        this.ipgSoapConnector = ipgSoapConnector;
    }

    @Override
    public IpgResponse bpPayRequest(BpPayRequest bpPayRequest) {
        /* call webservice */
        BpPayRequestResponse response = ipgSoapConnector.callWebService(BpPayRequest.class, bpPayRequest);

        /* validate response */
        validateResponseNotEmpty(response);
        validateResponseReturnValue(response.getReturn(), bpPayRequest.getOrderId());

        /*resolve responseCode */
        BpResponseCode responseCode = BpResponseCode.resolve(response.getReturn().split(CommonConstants.COMMA)[NumberConstants.ZERO]);

        /* create response*/
        return new IpgResponse(response.getReturn().split(CommonConstants.COMMA)[NumberConstants.ONE], bpPayRequest.getOrderId(), responseCode);
    }

    @Override
    public IpgResponse bpVerifyRequest(BpVerifyRequest bpVerifyRequest) {
        /* call webservice */
        BpVerifyRequestResponse response = ipgSoapConnector.callWebService(BpVerifyRequest.class, bpVerifyRequest);

        /* validate response */
        validateResponseNotEmpty(response);
        validateResponseReturnValue(response.getReturn(), bpVerifyRequest.getOrderId());

        /* create response*/
        return new IpgResponse(BpResponseCode.resolve(response.getReturn()));
    }

    @Override
    public IpgResponse bpSettleRequest(BpSettleRequest bpSettleRequest) {
        /* call webservice */
        BpSettleRequestResponse response = ipgSoapConnector.callWebService(BpSettleRequest.class, bpSettleRequest);

        /* validate response */
        validateResponseNotEmpty(response);
        validateResponseReturnValue(response.getReturn(), bpSettleRequest.getOrderId());

        /* create response*/
        return new IpgResponse(BpResponseCode.resolve(response.getReturn()));
    }

    private void validateResponseNotEmpty(Object response) {
        if (AppUtil.isNull(response))
            throw new IpgMellatInvalidResponseException("IPG BpPayRequestResponse or its return value is empty!");
    }

    private void validateResponseReturnValue(String returnValue, Long orderId) {
        if (AppUtil.isNull(returnValue) || returnValue.isEmpty())
            throw new IpgMellatInvalidResponseException("IPG BpPayRequestResponse or its return value is empty!");

        if (!returnValue.contains(CommonConstants.COMMA) &&
                !BpResponseCode.SUCCESS.getCode().equals(returnValue.substring(NumberConstants.ZERO).trim()))
            throw new IpgMellatInvalidDataFormatException(returnValue + ", orderId=" + orderId);
    }
}

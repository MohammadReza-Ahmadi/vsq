package com.vosouq.paymentgateway.mellat.validation;

import com.vosouq.paymentgateway.mellat.exception.InvalidMethodParameterException;
import com.vosouq.paymentgateway.mellat.exception.ipg.IpgMellatInvalidResponseException;
import com.vosouq.paymentgateway.mellat.exception.ipg.IpgMellatResponseNotMatchException;
import com.vosouq.paymentgateway.mellat.model.ipg.BpPayment;
import com.vosouq.paymentgateway.mellat.model.ipg.domainobject.IpgCallbackParameters;
import org.springframework.util.StringUtils;

import java.util.Objects;


public class BpPayValidator {

    public static void validateBpPayResponse(BpPayment bpPayment, IpgCallbackParameters ipgCallbackParameters) {
        if (Objects.isNull(ipgCallbackParameters))
            throw new IpgMellatInvalidResponseException("callback parameters are null!");

        if (StringUtils.isEmpty(ipgCallbackParameters.getResCod()))
            throw new IpgMellatInvalidResponseException("IPG resCod is empty!");

        if (StringUtils.isEmpty(ipgCallbackParameters.getRefId()))
            throw new IpgMellatInvalidResponseException("IPG RefId is empty!");

        if (StringUtils.isEmpty(ipgCallbackParameters.getSaleOrderId()))
            throw new IpgMellatInvalidResponseException("IPG SaleOrderId is empty!");

        if (Objects.isNull(bpPayment))
            throw new InvalidMethodParameterException("bpPayment parameter is null!");

        if (!Objects.equals(ipgCallbackParameters.getRefId(), bpPayment.getRefId()))
            throw new IpgMellatResponseNotMatchException("IPG RefId is not matched!");

        if (!Objects.equals(ipgCallbackParameters.getSaleOrderId(), bpPayment.getOrderSequence().getOrderId()))
            throw new IpgMellatResponseNotMatchException("IPG SaleOrderId is not matched!");
    }


}

package com.vosouq.paymentgateway.mellat.service.builder;


import com.ghasemkiani.util.icu.PersianCalendar;
import com.vosouq.paymentgateway.mellat.constant.CommonConstants;
import com.vosouq.paymentgateway.mellat.model.ipg.BpPayment;
import com.vosouq.paymentgateway.mellat.webservice.ipg.gen.BpPayRequest;
import com.vosouq.paymentgateway.mellat.webservice.ipg.gen.BpSettleRequest;
import com.vosouq.paymentgateway.mellat.webservice.ipg.gen.BpVerifyRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.vosouq.paymentgateway.mellat.constant.NumberConstants.FOUR;
import static com.vosouq.paymentgateway.mellat.constant.NumberConstants.TWO;

@Component
public class IpgCallSendingObjectBuilder {

    @Value("${ipg.shapark.terminalid}")
    private Long terminalId;
    @Value("${ipg.shapark.username}")
    private String userName;
    @Value("${ipg.shapark.password}")
    private String password;
    @Value("${ipg.shapark.payerid}")
    private String payerId;
    @Value("${ipg.shapark.callbackurl}")
    private String callBackUrl;
    @Value("${ipg.shapark.dateformat}")
    private String dateFormat;
    @Value("${ipg.shapark.timeformat}")
    private String timeFormat;

    public BpPayRequest buildBpPayRequest(BpPayment bpPayment) {
        BpPayRequest payRequest = new BpPayRequest();
        /* set static data*/
        payRequest.setTerminalId(terminalId);
        payRequest.setUserName(userName);
        payRequest.setUserPassword(password);
        payRequest.setCallBackUrl(callBackUrl);
        payRequest.setPayerId(payerId);

        /* set dynamic data*/
        payRequest.setOrderId(bpPayment.getOrderSequence().getOrderId());
        payRequest.setAmount(bpPayment.getAmount().longValue());
        payRequest.setLocalDate(getPersianFormattedDateString(bpPayment.getLocalDateTime()));
        payRequest.setLocalTime(getPersianFormattedTimeString(bpPayment.getLocalDateTime()));
        return payRequest;
    }

    public BpVerifyRequest buildBpVerifyRequest(BpPayment bpPayment) {
        BpVerifyRequest payVerify = new BpVerifyRequest();
        /* set static data*/
        payVerify.setTerminalId(terminalId);
        payVerify.setUserName(userName);
        payVerify.setUserPassword(password);

        /* set dynamic data*/
        payVerify.setOrderId(bpPayment.getOrderSequence().getOrderId());
        payVerify.setSaleOrderId(bpPayment.getOrderSequence().getOrderId());
        payVerify.setSaleReferenceId(bpPayment.getSaleReferenceId());
        return payVerify;
    }

    public BpSettleRequest buildBpSettleRequest(BpPayment bpPayment) {
        BpSettleRequest bpSettle = new BpSettleRequest();
        /* set static data*/
        bpSettle.setTerminalId(terminalId);
        bpSettle.setUserName(userName);
        bpSettle.setUserPassword(password);

        /* set dynamic data*/
        bpSettle.setOrderId(bpPayment.getOrderSequence().getOrderId());
        bpSettle.setSaleOrderId(bpPayment.getOrderSequence().getOrderId());
        bpSettle.setSaleReferenceId(bpPayment.getSaleReferenceId());
        return bpSettle;
    }

    private String getPersianFormattedDateString(Date date) {
        PersianCalendar persianCalendar = new PersianCalendar(date);
        return getPaddedValue(persianCalendar.get(Calendar.YEAR), FOUR) + CommonConstants.EMPTY_STR +
                getPaddedValue((persianCalendar.get(Calendar.MONTH) + 1), TWO) + CommonConstants.EMPTY_STR +
                getPaddedValue(persianCalendar.get(Calendar.DAY_OF_MONTH), TWO);
    }

    private String getPersianFormattedTimeString(Date date) {
        PersianCalendar persianCalendar = new PersianCalendar(date);
        return getPaddedValue(persianCalendar.get(Calendar.HOUR_OF_DAY), TWO) + CommonConstants.EMPTY_STR +
                getPaddedValue(persianCalendar.get(Calendar.MINUTE), TWO) + CommonConstants.EMPTY_STR +
            getPaddedValue(persianCalendar.get(Calendar.SECOND), TWO);
    }

    private String getFormattedStringDateTime(Date date, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    private String getPaddedValue(int value, int validLength) {
        String str = String.valueOf(value);
        while (str.length() < validLength)
            str = "0" + str;
        return str;
    }

}

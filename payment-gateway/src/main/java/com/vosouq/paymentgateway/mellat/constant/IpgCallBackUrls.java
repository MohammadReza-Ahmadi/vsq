package com.vosouq.paymentgateway.mellat.constant;

/**
 * @author <a href="mailto:m.reza79ahmadi@gmail.com">MohammadReza Ahmadi</a>
 * 22.08.20
 */


public interface IpgCallBackUrls {
    String APP_URL_PORT = "http://localhost:8086/payment-gateway";
    String BP_PAY_RESPONSE_URL = APP_URL_PORT + "ipg-response/bp-pay-response/";
}

package com.vosouq.paymentgateway.mellat.webservice.ipg;

import java.math.BigDecimal;

public interface IBANWebService {

    String ibPayRequest(String iban, BigDecimal amount);
}

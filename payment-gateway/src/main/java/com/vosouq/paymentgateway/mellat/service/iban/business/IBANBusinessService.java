package com.vosouq.paymentgateway.mellat.service.iban.business;

import java.math.BigDecimal;

public interface IBANBusinessService {

    String paymentRequest(String iban, BigDecimal amount);

}

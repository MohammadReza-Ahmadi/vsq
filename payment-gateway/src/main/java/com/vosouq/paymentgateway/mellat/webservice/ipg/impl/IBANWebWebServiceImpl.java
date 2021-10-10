package com.vosouq.paymentgateway.mellat.webservice.ipg.impl;

import com.vosouq.paymentgateway.mellat.webservice.ipg.IBANWebService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class IBANWebWebServiceImpl implements IBANWebService {

    @Override
    public String ibPayRequest(String iban, BigDecimal amount) {
        /*todo: should be implemented when we have access to bank's iban webservice wsdl file*/

        /* A return value of zero means that the money transfer operation
        at the relevant bank has been completed successfully.*/
        return "0";
    }
}

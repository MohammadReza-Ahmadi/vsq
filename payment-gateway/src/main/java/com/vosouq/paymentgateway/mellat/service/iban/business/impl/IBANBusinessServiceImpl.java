package com.vosouq.paymentgateway.mellat.service.iban.business.impl;

import com.vosouq.paymentgateway.mellat.service.iban.business.IBANBusinessService;
import com.vosouq.paymentgateway.mellat.webservice.ipg.IBANWebService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class IBANBusinessServiceImpl implements IBANBusinessService {

    private final IBANWebService ibanWebService;

    public IBANBusinessServiceImpl(IBANWebService ibanWebService) {
        this.ibanWebService = ibanWebService;
    }


    @Override
    public String paymentRequest(String iban, BigDecimal amount) {
        log.debug("handling payment request through iban channel");
        return ibanWebService.ibPayRequest(iban,amount);
    }
}

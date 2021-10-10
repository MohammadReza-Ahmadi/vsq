package com.vosouq.bookkeeping.service.crud;

import com.vosouq.bookkeeping.enumeration.CurrencyCode;
import com.vosouq.bookkeeping.model.journalizing.Currency;


public interface CurrencyCrudService {

    Currency save(Currency currency);

    Currency findByCode(CurrencyCode currencyCode);

}

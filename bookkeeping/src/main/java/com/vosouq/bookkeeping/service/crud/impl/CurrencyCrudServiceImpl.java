package com.vosouq.bookkeeping.service.crud.impl;

import com.vosouq.bookkeeping.enumeration.CurrencyCode;
import com.vosouq.bookkeeping.model.journalizing.Currency;
import com.vosouq.bookkeeping.repository.CurrencyRepository;
import com.vosouq.bookkeeping.service.crud.CurrencyCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class CurrencyCrudServiceImpl implements CurrencyCrudService {

    private final CurrencyRepository currencyRepository;

    public CurrencyCrudServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @PostConstruct
    private void init() {
        Currency c1 = new Currency();
        c1.setCode(CurrencyCode.IRR);
        c1.setSymbol("ریال");
        c1.setTitle("Iran Rial");
        save(c1);

        Currency c2 = new Currency();
        c2.setCode(CurrencyCode.USD);
        c2.setSymbol("$");
        c2.setTitle("US Dollar");
        save(c2);
    }

    @Override
    public Currency save(Currency currency) {
        log.info("Saving {}", currency);
        return currencyRepository.save(currency);
    }


    @Override
    public Currency findByCode(CurrencyCode currencyCode) {
        log.info("[{}] Finding currency by code: {}", this.getClass().getSimpleName() ,currencyCode);
        return currencyRepository.findByCode(currencyCode);
    }
}

package com.vosouq.bookkeeping.repository;

import com.vosouq.bookkeeping.enumeration.CurrencyCode;
import com.vosouq.bookkeeping.model.journalizing.Currency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency,CurrencyCode> {

    Currency findByCode(CurrencyCode currencyCode);

}

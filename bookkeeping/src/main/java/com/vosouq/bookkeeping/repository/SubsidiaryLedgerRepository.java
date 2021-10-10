package com.vosouq.bookkeeping.repository;

import com.vosouq.bookkeeping.model.journalizing.SubsidiaryLedger;
import org.springframework.data.repository.CrudRepository;


public interface SubsidiaryLedgerRepository extends CrudRepository<SubsidiaryLedger,Integer> {

    SubsidiaryLedger findByCode(Integer code);
}

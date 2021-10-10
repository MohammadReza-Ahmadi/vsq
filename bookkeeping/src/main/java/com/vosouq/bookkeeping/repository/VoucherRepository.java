package com.vosouq.bookkeeping.repository;

import com.vosouq.bookkeeping.model.journalizing.SubsidiaryLedger;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherRepository extends CrudRepository<SubsidiaryLedger, Long> {

}

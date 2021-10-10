package com.vosouq.bookkeeping.repository;

import com.vosouq.bookkeeping.model.IbanAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IbanAccountRepository extends CrudRepository<IbanAccount,Long> {

    IbanAccount findByIban(String iban);

    Optional<List<IbanAccount>> findAllByUserId(Long userId);
}

package com.vosouq.bookkeeping.repository;

import com.vosouq.bookkeeping.model.journalizing.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    Account save(Account account);

    Optional<Account> findById(Long id);

    Account findByUserId(Long userId);

    @Query("select a from Account a where a.userId = ?1")
    Account getByUserId(Long userId);
}

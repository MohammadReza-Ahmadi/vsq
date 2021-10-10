package com.vosouq.bookkeeping.repository;

import com.vosouq.bookkeeping.model.journalizing.JournalizingPage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface JournalizingPageRepository extends CrudRepository<JournalizingPage,Long> {

    JournalizingPage findByDate(Date date);

    JournalizingPage findFirstByOrderByDateDesc();

    List<JournalizingPage> findByDateLessThanEqualOrderByDateDesc(Date date);
}

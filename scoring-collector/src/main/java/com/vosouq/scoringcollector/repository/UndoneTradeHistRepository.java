package com.vosouq.scoringcollector.repository;

import com.vosouq.scoringcollector.model.hist.UndoneTradeHist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UndoneTradeHistRepository extends MongoRepository<UndoneTradeHist, String> {

//    UndoneTrade findByUserId(Long userId);

}
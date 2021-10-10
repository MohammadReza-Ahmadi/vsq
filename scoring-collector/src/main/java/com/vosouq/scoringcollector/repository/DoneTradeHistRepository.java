package com.vosouq.scoringcollector.repository;

import com.vosouq.scoringcollector.model.hist.DoneTradeHist;
import com.vosouq.scoringcollector.model.hist.UndoneTradeHist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoneTradeHistRepository extends MongoRepository<DoneTradeHist, String> {

//    UndoneTrade findByUserId(Long userId);

}
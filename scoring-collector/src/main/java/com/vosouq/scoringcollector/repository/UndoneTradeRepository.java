package com.vosouq.scoringcollector.repository;

import com.vosouq.scoringcollector.model.payload.producing.UndoneTrade;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UndoneTradeRepository extends MongoRepository<UndoneTrade, String> {

    UndoneTrade findByUserId(Long userId);

}
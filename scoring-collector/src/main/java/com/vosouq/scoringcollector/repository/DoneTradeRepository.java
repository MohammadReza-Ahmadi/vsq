package com.vosouq.scoringcollector.repository;

import com.vosouq.scoringcollector.model.payload.producing.DoneTrade;
import com.vosouq.scoringcollector.model.payload.producing.UndoneTrade;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoneTradeRepository extends MongoRepository<DoneTrade, String> {

    DoneTrade findByUserId(Long userId);

}
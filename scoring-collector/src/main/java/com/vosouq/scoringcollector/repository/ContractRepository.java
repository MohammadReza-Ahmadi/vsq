package com.vosouq.scoringcollector.repository;

import com.vosouq.scoringcollector.model.payload.consuming.Contract;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends MongoRepository<Contract, String> {


}
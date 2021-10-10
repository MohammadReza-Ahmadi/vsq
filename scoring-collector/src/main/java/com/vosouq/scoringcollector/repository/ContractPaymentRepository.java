package com.vosouq.scoringcollector.repository;

import com.vosouq.scoringcollector.model.payload.consuming.ContractPayment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractPaymentRepository extends MongoRepository<ContractPayment, String> {

    ContractPayment findByPaymentId(Long paymentId);

}
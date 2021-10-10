package com.vosouq.paymentgateway.mellat.repository;

import com.vosouq.paymentgateway.mellat.model.ipg.BpPayment;
import com.vosouq.paymentgateway.mellat.model.ipg.OrderSequence;
import org.springframework.data.repository.CrudRepository;

public interface OrderSequenceRepository extends CrudRepository<OrderSequence,Long> {

    OrderSequence findByOrderId(Long orderId);
}

package com.vosouq.paymentgateway.mellat.repository;

import com.vosouq.paymentgateway.mellat.model.ipg.BpPayment;
import com.vosouq.paymentgateway.mellat.model.ipg.OrderSequence;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author MohammadReza Ahmadi
 * @since 9/11/2020
 */

@Repository
public interface BpPaymentRepository extends CrudRepository<BpPayment,Long> {

    BpPayment findByRequesterId(Long requesterId);

    BpPayment findByOrderSequence(OrderSequence orderSequence);

    BpPayment findByRefIdAndOrderSequence(String refId, OrderSequence orderSequence);
}

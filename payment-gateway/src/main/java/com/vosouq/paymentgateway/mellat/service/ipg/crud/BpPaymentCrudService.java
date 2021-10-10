package com.vosouq.paymentgateway.mellat.service.ipg.crud;

import com.vosouq.paymentgateway.mellat.model.ipg.BpPayment;
import com.vosouq.paymentgateway.mellat.model.ipg.OrderSequence;


public interface BpPaymentCrudService {

    BpPayment save(BpPayment bpPayment);

    BpPayment findById(Long bpPayRequestId);

    BpPayment findByRefIdAndOrderSequence(String refId, OrderSequence orderSequence);
}

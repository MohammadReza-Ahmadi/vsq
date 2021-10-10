package com.vosouq.contract.service;

import com.vosouq.contract.model.Contract;
import com.vosouq.contract.model.Payment;
import com.vosouq.contract.model.PaymentTemplateModel;

import java.util.List;

public interface PaymentService {

    Payment findById(Long paymentId);

    void saveContractPayments(Contract contract, List<PaymentTemplateModel> paymentTemplates);

    Payment getCurrentPayment(Contract contract);

    Boolean isCurrentPaymentTheLastOne(Contract contract, Payment currentPayment);

    void doPayment(Payment payment);

}

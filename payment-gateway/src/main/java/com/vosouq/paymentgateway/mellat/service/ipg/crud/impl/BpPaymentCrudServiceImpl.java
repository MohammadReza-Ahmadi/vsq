package com.vosouq.paymentgateway.mellat.service.ipg.crud.impl;

import com.vosouq.paymentgateway.mellat.model.ipg.BpPayment;
import com.vosouq.paymentgateway.mellat.model.ipg.OrderSequence;
import com.vosouq.paymentgateway.mellat.repository.BpPaymentRepository;
import com.vosouq.paymentgateway.mellat.service.ipg.crud.BpPaymentCrudService;
import com.vosouq.paymentgateway.mellat.webservice.ipg.IPGWebService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author <a href="mailto:m.reza79ahmadi@gmail.com">MohammadReza Ahmadi</a>
 * 19.08.20
 */

@Service
public class BpPaymentCrudServiceImpl implements BpPaymentCrudService {

    private BpPaymentRepository bpPaymentRepository;
    private IPGWebService ipgWebService;


    public BpPaymentCrudServiceImpl(BpPaymentRepository bpPaymentRepository, IPGWebService ipgWebService) {
        this.bpPaymentRepository = bpPaymentRepository;
        this.ipgWebService = ipgWebService;
    }

    /**
     * save test data in db
     */
//    @PostConstruct
    private void init() {
        BpPayment bpPayment = new BpPayment();
        bpPayment.setAdditionalData("a sample bpPayRequest created in launch time");
        bpPaymentRepository.save(bpPayment);
    }

    @Override
    public BpPayment save(BpPayment bpPayment) {
        return bpPaymentRepository.save(bpPayment);
    }

    @Override
    public BpPayment findById(Long bpPayRequestId) {
        Optional<BpPayment> bpPayRequestOptional = bpPaymentRepository.findById(bpPayRequestId);
        return bpPayRequestOptional.orElse(null);
    }

    @Override
    public BpPayment findByRefIdAndOrderSequence(String refId, OrderSequence orderSequence) {
        return bpPaymentRepository.findByRefIdAndOrderSequence(refId,orderSequence);
    }
}

package com.vosouq.contract.service.impl;

import com.vosouq.contract.exception.BadPaymentException;
import com.vosouq.contract.exception.IncompatibleValueException;
import com.vosouq.contract.exception.PaymentNotFoundException;
import com.vosouq.contract.exception.PriceException;
import com.vosouq.contract.model.*;
import com.vosouq.contract.repository.PaymentRepository;
import com.vosouq.contract.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.vosouq.contract.utills.TimeUtil.nowInTimestamp;

@Slf4j
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment findById(Long paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow(PaymentNotFoundException::new);
    }

    private void validatePayments(Contract contract, List<PaymentTemplateModel> paymentTemplates) {

        Long price = contract.getPrice();
        long calculatedPrice;

        calculatedPrice = paymentTemplates.stream().mapToLong(PaymentTemplateModel::getAmount).sum();
        if (!price.equals(calculatedPrice))
            throw new PriceException();

        if (contract instanceof CommodityContract) {
            CommodityContract commodity = (CommodityContract) contract;
            calculatedPrice = commodity.getPricePerUnit() * commodity.getQuantity();
            if (!price.equals(calculatedPrice)) {
                log.debug("Payment error, price: {} is not equal to calculated price: {}", price, calculatedPrice);
                throw new BadPaymentException();
            }
        }
    }

    private void saveAll(List<Payment> payments) {
        paymentRepository.saveAll(payments);
    }

    @Override
    public void saveContractPayments(Contract contract, List<PaymentTemplateModel> paymentTemplates) {

        validatePayments(contract, paymentTemplates);

        contract.setMultiStepPayment(paymentTemplates.size() > 1);
        contract.setPayments(convertToPayments(paymentTemplates, contract));

        saveAll(contract.getPayments());
    }

    @Override
    public Payment getCurrentPayment(Contract contract) {
        return contract.getPayments()
                .stream()
                .sorted(Comparator.comparingLong(Payment::getId))
                .filter(payment -> payment.getStatus().equals(PaymentStatus.UNPAID))
                .findFirst()
                .orElseThrow(IncompatibleValueException::new);
    }

    @Override
    public Boolean isCurrentPaymentTheLastOne(Contract contract, Payment currentPayment) {
        List<Payment> payments = contract.getPayments();

        if (payments.size() == 1)
            return true;
        else {

            List<Payment> sortedPayments =
                    payments.stream()
                            .sorted(Comparator.comparingLong(Payment::getId))
                            .collect(Collectors.toList());

            return (payments.size() - 1) == sortedPayments.indexOf(currentPayment);
        }
    }

    @Override
    public void doPayment(Payment payment) {

        payment = findById(payment.getId());

        payment.setUuid(UUID.randomUUID());
        payment.setPaymentDate(nowInTimestamp());

        payment.setIsDelayed(payment.getPaymentDate().after(payment.getDueDate())
                ? Boolean.TRUE
                : Boolean.FALSE);
        payment.setStatus(PaymentStatus.PAID);
        payment.setUpdateDate(nowInTimestamp());

        paymentRepository.save(payment);
    }

    private List<Payment> convertToPayments(List<PaymentTemplateModel> paymentTemplates, Contract contract) {
        return paymentTemplates
                .stream()
                .map(paymentTemplate -> {
                    Payment payment = new Payment();
                    payment.setContract(contract);
                    payment.setAmount(paymentTemplate.getAmount());
                    payment.setDueDate(paymentTemplate.getDueDate());
                    payment.setStatus(PaymentStatus.UNPAID);
                    payment.setCreateDate(nowInTimestamp());
                    return payment;
                })
                .collect(Collectors.toList());
    }

}

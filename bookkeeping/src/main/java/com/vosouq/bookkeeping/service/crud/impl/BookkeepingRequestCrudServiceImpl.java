package com.vosouq.bookkeeping.service.crud.impl;

import com.vosouq.bookkeeping.enumeration.GatewayType;
import com.vosouq.bookkeeping.enumeration.RequestStatus;
import com.vosouq.bookkeeping.enumeration.RequestType;
import com.vosouq.bookkeeping.enumeration.RequesterType;
import com.vosouq.bookkeeping.model.BookkeepingRequest;
import com.vosouq.bookkeeping.model.journalizing.Account;
import com.vosouq.bookkeeping.repository.BookkeepingRequestRepository;
import com.vosouq.bookkeeping.service.crud.BookkeepingRequestCrudService;
import com.vosouq.bookkeeping.util.AppUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class BookkeepingRequestCrudServiceImpl implements BookkeepingRequestCrudService {

    private final BookkeepingRequestRepository bookkeepingRequestRepository;

    public BookkeepingRequestCrudServiceImpl(BookkeepingRequestRepository bookkeepingRequestRepository) {
        this.bookkeepingRequestRepository = bookkeepingRequestRepository;
    }

    //    @PostConstruct
    private void init() {
        BookkeepingRequest bookkeepingRequest = new BookkeepingRequest();
        bookkeepingRequest.setRequesterId(123L);
        bookkeepingRequest.setRequesterType(RequesterType.CONTRACT);
        bookkeepingRequest.setRequestType(RequestType.ACCOUNT_RECHARGE);
        bookkeepingRequest.setGatewayType(GatewayType.IPG);
        bookkeepingRequest.setRequestDate(new Date());
        bookkeepingRequest.setAmount(BigDecimal.valueOf(500000L));
        bookkeepingRequest.setDescription("request-1 from contract service");
        createOrUpdate(bookkeepingRequest);
    }

    @Override
    public BookkeepingRequest getById(Long id) {
        return bookkeepingRequestRepository.findById(id).orElseGet(null);
    }

    @Override
    public BookkeepingRequest findByGatewayOrderId(Long id) {
        return bookkeepingRequestRepository.findByGatewayOrderId(id);
    }

    @Override
    public BookkeepingRequest findBookkeepingRequestByGatewayRefId(String gatewayRefId) {
        return bookkeepingRequestRepository.findByGatewayRefId(gatewayRefId);
    }

    @Override
    public List<BookkeepingRequest> getAllBookkeepingRequestsByRequesterId(Long requesterId) {
        return bookkeepingRequestRepository.findAllByRequesterIdOrderByRequestDate(requesterId).orElse(AppUtil.getEmptyList());
    }

    @Override
    public List<BookkeepingRequest> getAllByRequesterIdAndDebitAccountOrCreditAccount(Long requesterId, Account account) {
        log.info("get BookkeepingRequest by requesterId:{} and debit or credit account id:{}", requesterId, account.getId());
        return bookkeepingRequestRepository.findAllByRequesterIdAndDebitAccountOrCreditAccountOrderByRequestDate(requesterId, account, account).orElse(AppUtil.getEmptyList());
    }

    @Override
    public List<BookkeepingRequest> getAllByRequesterIdAndDebitAccountAndRequestTypes(Long requesterId, Account debitAccount, List<RequestType> requestTypes) {
        log.info("get BookkeepingRequest by requesterId:{} and debit account id:{}", requesterId, debitAccount.getId());
        return bookkeepingRequestRepository.findAllByRequesterIdAndDebitAccountAndRequestTypeInOrderByRequestDate(requesterId, debitAccount, requestTypes).orElse(AppUtil.getEmptyList());
    }

    @Override
    public List<BookkeepingRequest> getAllByRequesterIdAndCreditAccountAndRequestTypes(Long requesterId, Account creditAccount, List<RequestType> requestTypes) {
        log.info("get BookkeepingRequest by requesterId:{} and credit account id:{}", requesterId, creditAccount.getId());
        return bookkeepingRequestRepository.findAllByRequesterIdAndCreditAccountAndRequestTypeInOrderByRequestDate(requesterId, creditAccount,requestTypes).orElse(AppUtil.getEmptyList());
    }

    @Override
    public List<BookkeepingRequest> getAllByRequesterIdAndDebitAccountOrCreditAccountAndRequestTypes(Long requesterId, Account account, List<RequestType> requestTypes) {
        log.info("get BookkeepingRequest by requesterId:{} and debit or credit account id:{}", requesterId, account.getId());
        return bookkeepingRequestRepository.findAllByRequesterIdAndDebitAccountOrCreditAccountAndRequestTypeInOrderByRequestDate(requesterId, account, account,requestTypes).orElse(AppUtil.getEmptyList());
    }

    @Override
    public List<BookkeepingRequest> getAllByRequesterIdAndPostingModelCode(Long requesterId, RequestType requestType, RequestStatus requestStatus) {
        log.info("get BookkeepingRequest by requesterId:{} and PostingModelCode:{}", requesterId, requestType);
//        return bookkeepingRequestRepository.findByRequesterIdAndRequestTypeOrderByRequestDate(requesterId, requestType).orElse(AppUtil.getEmptyList());
        return bookkeepingRequestRepository.findByRequesterIdAndRequestTypeAndRequestStatusOrderByRequestDate(requesterId, requestType, requestStatus).orElse(AppUtil.getEmptyList());
    }

    @Override
    public BookkeepingRequest createOrUpdate(BookkeepingRequest bookkeepingRequest) {
        log.info("saving PaymentRequest: {}", bookkeepingRequest);
        return bookkeepingRequestRepository.save(bookkeepingRequest);
    }
}

package com.vosouq.bookkeeping.controller.mapper;

import com.vosouq.bookkeeping.controller.dto.responses.BookkeepingPaymentRequestResponse;
import com.vosouq.bookkeeping.controller.dto.requests.GetIpgPaymentRefIdRequest;
import com.vosouq.bookkeeping.controller.dto.requests.PayRequest;
import com.vosouq.bookkeeping.controller.dto.responses.PaymentResponse;
import com.vosouq.bookkeeping.enumeration.GatewayType;
import com.vosouq.bookkeeping.enumeration.RequestStatus;
import com.vosouq.bookkeeping.enumeration.RequesterType;
import com.vosouq.bookkeeping.model.BookkeepingRequest;
import com.vosouq.bookkeeping.model.journalizing.Account;
import com.vosouq.bookkeeping.model.journalizing.SubsidiaryLedger;
import com.vosouq.bookkeeping.model.posting.PostingDebitCreditSubsidiaryLedgers;
import com.vosouq.bookkeeping.enumeration.RequestType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class BookkeepingRequestMapper {


    public static BookkeepingRequest map(RequesterType requesterType, Long userId, PostingDebitCreditSubsidiaryLedgers postingDebitCreditSubsidiaryLedgers, BigDecimal amount) {
        return map(requesterType,userId, postingDebitCreditSubsidiaryLedgers, null, null, amount);
    }

    public static BookkeepingRequest map(RequesterType requesterType, Long userId, PostingDebitCreditSubsidiaryLedgers postingDebitCreditSubsidiaryLedgers, Account debitAccount, Account creditAccount, BigDecimal amount) {
        BookkeepingRequest bookkeepingRequest = new BookkeepingRequest();
        bookkeepingRequest.setUserId(userId);
        bookkeepingRequest.setRequesterType(requesterType);
        bookkeepingRequest.setRequestType(postingDebitCreditSubsidiaryLedgers.getRequestType());
        bookkeepingRequest.setDebitSubsidiaryLedger(postingDebitCreditSubsidiaryLedgers.getDebitSubsidiaryLedger());
        bookkeepingRequest.setCreditSubsidiaryLedger(postingDebitCreditSubsidiaryLedgers.getCreditSubsidiaryLedger());
        bookkeepingRequest.setDebitAccount(debitAccount);
        bookkeepingRequest.setCreditAccount(creditAccount);
        bookkeepingRequest.setRequestDate(new Date());
        bookkeepingRequest.setAmount(amount);
        return bookkeepingRequest;
    }

    public static BookkeepingPaymentRequestResponse mapToBookkeepingPayRequestResponse(BookkeepingRequest bookkeepingRequest){
        return new BookkeepingPaymentRequestResponse(bookkeepingRequest.getAmount());
    }


    public static BookkeepingRequest mapForChargeAccount(Long userId, Account creditAccount, Account debitAccount, BigDecimal amount) {
        BookkeepingRequest bookkeepingRequest = new BookkeepingRequest();
        bookkeepingRequest.setUserId(userId);
        bookkeepingRequest.setRequesterId(userId);
        bookkeepingRequest.setRequesterType(RequesterType.ACCOUNT);
        bookkeepingRequest.setRequestStatus(RequestStatus.SUCCESS);
        bookkeepingRequest.setCreditAccount(creditAccount);
        bookkeepingRequest.setCreditSubsidiaryLedger(creditAccount.getSubsidiaryLedger());
        bookkeepingRequest.setDebitAccount(debitAccount);
        bookkeepingRequest.setDebitSubsidiaryLedger(debitAccount.getSubsidiaryLedger());
        bookkeepingRequest.setRequestDate(new Date());
        bookkeepingRequest.setDescription("charge account");
        bookkeepingRequest.setAmount(amount);
        return bookkeepingRequest;
    }

    public static BookkeepingRequest mapForCreateContract(Long userId, SubsidiaryLedger debitSubsidiaryLedger, SubsidiaryLedger creditSubsidiaryLedger, BigDecimal amount) {
        BookkeepingRequest bookkeepingRequest = new BookkeepingRequest();
        bookkeepingRequest.setUserId(userId);
        bookkeepingRequest.setRequesterId(userId);
        bookkeepingRequest.setRequesterType(RequesterType.CONTRACT);
        bookkeepingRequest.setRequestStatus(RequestStatus.SUCCESS);
        bookkeepingRequest.setDebitSubsidiaryLedger(debitSubsidiaryLedger);
        bookkeepingRequest.setCreditSubsidiaryLedger(creditSubsidiaryLedger);
        bookkeepingRequest.setRequestDate(new Date());
        bookkeepingRequest.setDescription("create contract");
        bookkeepingRequest.setAmount(amount);
        return bookkeepingRequest;
    }

    public static BookkeepingRequest map(PayRequest payRequest, GatewayType gatewayType) {
        return BookkeepingRequest.builder()
                .requestStatus(RequestStatus.PENDING)
                .requesterId(payRequest.getRequesterId())
                .requesterType(payRequest.getRequesterType())
                .requestType(RequestType.CONTRACT_PAYMENT)
                .gatewayType(gatewayType)
                .amount(payRequest.getAmount())
                .requestDate(new Date())
                .description(payRequest.getDescription())
                .build();
    }

    public static GetIpgPaymentRefIdRequest mapToGatewayPayRequest(BookkeepingRequest bookkeepingRequest) {
        return GetIpgPaymentRefIdRequest.builder()
                .requesterId(bookkeepingRequest.getRequesterId())
                .amount(bookkeepingRequest.getAmount())
                .build();
    }

    public static PaymentResponse map(BookkeepingRequest bookkeepingRequest) {
        return PaymentResponse.builder()
                .paymentId(bookkeepingRequest.getId())
                .requesterId(bookkeepingRequest.getRequesterId())
                .paymentStatus(bookkeepingRequest.getRequestStatus().toString())
                .amount(bookkeepingRequest.getAmount())
                .requesterType(bookkeepingRequest.getRequesterType().toString())
                .paymentType(bookkeepingRequest.getRequestType().toString())
                .gatewayType(bookkeepingRequest.getGatewayType().toString())
                .requestDate(bookkeepingRequest.getRequestDate().getTime())
                .description(bookkeepingRequest.getDescription())
                .build();
    }

    public static List<PaymentResponse> map(List<BookkeepingRequest> bookkeepingRequestList) {
        List<PaymentResponse> paymentResponseList = new ArrayList<>(bookkeepingRequestList.size());
        for (BookkeepingRequest bookkeepingRequest : bookkeepingRequestList) {
            paymentResponseList.add(map(bookkeepingRequest));
        }
        return paymentResponseList;
    }

}

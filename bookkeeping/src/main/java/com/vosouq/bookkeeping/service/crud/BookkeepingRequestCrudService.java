package com.vosouq.bookkeeping.service.crud;

import com.vosouq.bookkeeping.enumeration.RequestStatus;
import com.vosouq.bookkeeping.enumeration.RequestType;
import com.vosouq.bookkeeping.model.BookkeepingRequest;
import com.vosouq.bookkeeping.model.journalizing.Account;

import java.util.List;

public interface BookkeepingRequestCrudService {

    BookkeepingRequest getById(Long paymentId);

    BookkeepingRequest findByGatewayOrderId(Long id);

    BookkeepingRequest findBookkeepingRequestByGatewayRefId(String gatewayRefId);

    List<BookkeepingRequest> getAllBookkeepingRequestsByRequesterId(Long requesterId);

    List<BookkeepingRequest> getAllByRequesterIdAndDebitAccountOrCreditAccount(Long requesterId, Account account);

    List<BookkeepingRequest> getAllByRequesterIdAndDebitAccountAndRequestTypes(Long requesterId, Account debitAccount, List<RequestType> requestTypes);

    List<BookkeepingRequest> getAllByRequesterIdAndCreditAccountAndRequestTypes(Long requesterId, Account creditAccount, List<RequestType> requestTypes);

    List<BookkeepingRequest> getAllByRequesterIdAndDebitAccountOrCreditAccountAndRequestTypes(Long requesterId, Account account, List<RequestType> requestTypes);

    List<BookkeepingRequest> getAllByRequesterIdAndPostingModelCode(Long requesterId, RequestType requestType, RequestStatus requestStatus);

    BookkeepingRequest createOrUpdate(BookkeepingRequest bookkeepingRequest);
}

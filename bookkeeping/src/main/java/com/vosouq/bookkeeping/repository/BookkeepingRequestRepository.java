package com.vosouq.bookkeeping.repository;

import com.vosouq.bookkeeping.enumeration.RequestStatus;
import com.vosouq.bookkeeping.model.BookkeepingRequest;
import com.vosouq.bookkeeping.enumeration.RequestType;
import com.vosouq.bookkeeping.model.journalizing.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BookkeepingRequestRepository extends CrudRepository<BookkeepingRequest, Long> {

    Optional<BookkeepingRequest> findById(Long id);

    BookkeepingRequest findByGatewayOrderId(Long id);

    BookkeepingRequest findByGatewayRefId(String id);

    Optional<List<BookkeepingRequest>> findAllByRequesterIdAndDebitAccountOrCreditAccountOrderByRequestDate(Long requesterId, Account debitAccount, Account creditAccount);

    Optional<List<BookkeepingRequest>> findAllByRequesterIdAndDebitAccountAndRequestTypeInOrderByRequestDate(Long requesterId, Account debitAccount,List<RequestType> requestTypes);

    Optional<List<BookkeepingRequest>> findAllByRequesterIdAndCreditAccountAndRequestTypeInOrderByRequestDate(Long requesterId, Account creditAccount,List<RequestType> requestTypes);

    Optional<List<BookkeepingRequest>> findAllByRequesterIdAndDebitAccountOrCreditAccountAndRequestTypeInOrderByRequestDate(Long requesterId, Account debitAccount, Account creditAccount, List<RequestType> requestTypes);

    Optional<List<BookkeepingRequest>> findByRequesterIdAndRequestTypeOrderByRequestDate(Long requesterId, RequestType requestType);

    Optional<List<BookkeepingRequest>> findByRequesterIdAndRequestTypeAndRequestStatusOrderByRequestDate(Long requesterId, RequestType requestType, RequestStatus requestStatus);

    Optional<List<BookkeepingRequest>> findAllByRequesterIdOrderByRequestDate(Long id);
}

package com.vosouq.bookkeeping.service.business;


import com.vosouq.bookkeeping.model.BookkeepingRequest;
import com.vosouq.bookkeeping.model.domainobject.VoucherFormulaAmount;
import com.vosouq.bookkeeping.model.posting.PostingDebitCreditSubsidiaryLedgers;
import com.vosouq.bookkeeping.model.posting.PostingModel;
import com.vosouq.bookkeeping.enumeration.RequestType;

public interface PostingModelBusinessService {

    PostingModel createPostingModel(RequestType requestType, BookkeepingRequest bookkeepingRequest, VoucherFormulaAmount voucherFormulaAmount);

    PostingDebitCreditSubsidiaryLedgers getDebitAndCreditSubsidiaryLedgers(RequestType requestType);
}

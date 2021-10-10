package com.vosouq.bookkeeping.service.business;

public interface ContractBusinessService {

    void create(Long contractId);

    void goodsDelivery(Long contractId);

    void settlement(Long contractId, Long sellerUserId);
}

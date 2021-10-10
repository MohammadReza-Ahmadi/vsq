package com.vosouq.bookkeeping.service.business;

import com.vosouq.bookkeeping.controller.dto.requests.PayRequest;
import com.vosouq.bookkeeping.controller.dto.responses.PayResponse;
import com.vosouq.bookkeeping.enumeration.GatewayType;
import com.vosouq.bookkeeping.model.BookkeepingRequest;

import java.math.BigDecimal;

public interface BookkeepingRequestBusinessService {

    BookkeepingRequest createAccountRechargeRequest(BigDecimal amount);

    BookkeepingRequest createAccountWithdrawalRequest(String iban, BigDecimal amount);

    BookkeepingRequest createContractRegistrationRequest(Long contractId);

    BookkeepingRequest createContractIpgPaymentRequest(Long contractId);

    BookkeepingRequest createContractAccountPaymentRequest(Long contractId);

    void createSuccessIpgPaymentRequest(Long gatewayOrderId);

    void createFailedIpgPaymentRequest(Long gatewayOrderId);

    BookkeepingRequest createContractGoodsDeliveryRequest(Long contractId);

    BookkeepingRequest createSettlementContractRequest(Long contractId, Long sellerUserId);

    PayResponse createBookkeepingRequest_DEMO(PayRequest payRequest, GatewayType gatewayType);

}

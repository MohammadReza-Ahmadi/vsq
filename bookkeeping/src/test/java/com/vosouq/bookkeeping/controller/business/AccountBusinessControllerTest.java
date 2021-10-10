package com.vosouq.bookkeeping.controller.business;

import com.vosouq.bookkeeping.AbstractTest;
import com.vosouq.bookkeeping.controller.MockDataValues;
import com.vosouq.bookkeeping.controller.dto.responses.AccountBalanceResponse;
import com.vosouq.bookkeeping.controller.dto.responses.AccountTurnoverResponse;
import com.vosouq.bookkeeping.controller.dto.responses.ContractIpgPaymentResponse;
import com.vosouq.bookkeeping.enumeration.AccountTransactionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;

public class AccountBusinessControllerTest extends AbstractTest {

    private final String baseUrl = "/accounts";

    @Value("${payment.gateway.ipg.prefix}")
    private String pgwIpgEndpointPrefix;

//    @MockBean
//    private PaymentGatewayRepository paymentGatewayRepository;

    @Test
    public void ipgRecharge() throws Exception {
        BigDecimal rechargeAmount = BigDecimal.valueOf(MockDataValues.rechargeAmount);
        String url = baseUrl + "/recharge/" + rechargeAmount;

        mockUserBusinessService();
//        mockPaymentGatewayRepository(new IpgResponse("1111111111111AFB", MockDataValues.orderId));
//        Mockito.when(paymentGatewayRepository.getIpgPaymentRequestRefId(new GetIpgPaymentRefIdRequest(MockDataValues.userId, BigDecimal.valueOf(MockDataValues.amount))))
//                .thenReturn(new IpgResponse("1111111111111AFB", MockDataValues.orderId));
        putCall(url);
        ContractIpgPaymentResponse response = mapFromJson(ContractIpgPaymentResponse.class);
//        MockDataValues.gatewayOrderId = response.getUrl().split("http://91.92.211.76:8013/ipg/mellat/")[1].split("/")[0];
        MockDataValues.gatewayOrderId = response.getUrl().split(pgwIpgEndpointPrefix)[1].split("/")[0];
        printResult(response);
    }

    @Test
    public void getWithdrawableBalance() throws Exception {
        String url = baseUrl + "/balance";

        mockUserBusinessService();
        getCall(url,HttpStatus.OK);
        AccountBalanceResponse response = mapFromJson(AccountBalanceResponse.class);
        printResult(response);
    }

    @Test
    public void getTurnovers() throws Exception {
        String transactionType = AccountTransactionType.DEPOSIT.toString();
//        String transactionType = AccountTransactionType.WITHDRAWAL.toString();
        String url = baseUrl + "/turnovers/" + transactionType;

        mockUserBusinessService();
        getCall(url);
        List<AccountTurnoverResponse> turnoverResponses = mapFromJsonToList(AccountTurnoverResponse.class);
        printResultList(turnoverResponses);
    }

    @Test
    public void getTurnoversOnContract() throws Exception {
//        String transactionType = AccountTransactionType.DEPOSIT.toString();
//        String transactionType = AccountTransactionType.WITHDRAWAL.toString();
//        String url = baseUrl + "/contract/" + MockDataValues.contractId  + "/turnovers/" + transactionType;
        String url = baseUrl + "/contract/" + MockDataValues.contractId  + "/turnovers";

        mockUserBusinessService();
        getCall(url, HttpStatus.OK);
        List<AccountTurnoverResponse> turnoverResponses = mapFromJsonToList(AccountTurnoverResponse.class);
        printResultList(turnoverResponses);
    }

    @Test
    public void withdrawalAccountAndDepositToIban() throws Exception {
        String url = baseUrl + "/" + MockDataValues.iban  + "/withdrawal/"+MockDataValues.settleAmount;

        mockUserBusinessService(MockDataValues.sellerUserId);
        putCall(url);
    }


}
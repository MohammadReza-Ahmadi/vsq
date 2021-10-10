package com.vosouq.bookkeeping.controller.business;

import com.vosouq.bookkeeping.controller.dto.responses.ContractIpgPaymentResponse;
import com.vosouq.bookkeeping.controller.MockDataValues;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;


public class ContractBusinessControllerTest extends AccountBusinessControllerTest {

    private final String baseUrl = "/contracts/";


    @Test
    public void create() throws Exception {
        String url = baseUrl + MockDataValues.contractId  + "/create/";

        mockUserBusinessService();
        mockContractRepository();
        postCall(url, HttpStatus.NO_CONTENT);
        mapFromJson(Object.class);
    }

    @Test
    public void ipgPaymentRequest() throws Exception {
        String url = baseUrl + MockDataValues.contractId  + "/ipg/payment/";

        mockUserBusinessService();
        mockContractRepository();
        postCall(url, HttpStatus.CREATED);
        ContractIpgPaymentResponse response = mapFromJson(ContractIpgPaymentResponse.class);
        printResult(response);
    }

    @Test
    public void accountPayment() throws Exception {
        String url = baseUrl +  MockDataValues.contractId  + "/account/payment";

        mockUserBusinessService();
        mockContractRepository();
        putCall(url);
        mapFromJson(Object.class);
    }

    @Test
   public void goodsDelivery() throws Exception {
        String url = baseUrl + MockDataValues.contractId + "/delivery/";

        mockUserBusinessService();
        mockContractRepository();
        putCall(url);
        mapFromJson(Object.class);
    }

    @Test
    public void settlement() throws Exception {
        String url = baseUrl +  MockDataValues.contractId  + "/settlement/" + MockDataValues.sellerUserId;

        mockUserBusinessService();
        mockContractRepository();
        postCall(url);
        mapFromJson(Object.class);
    }
}
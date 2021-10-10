package com.vosouq.bookkeeping.controller.ipg;

import com.vosouq.bookkeeping.controller.MockDataValues;
import com.vosouq.bookkeeping.controller.business.ContractBusinessControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class IpgMellatPGResponseControllerTest extends ContractBusinessControllerTest {

    private final String baseUrl = "/ipg";

    @Test
    public void paymentSuccess() throws Exception {
        String url = baseUrl + "/payment-success";

        mockUserBusinessService();
        mockContractRepository();
        doNothingWhenCallInformContractByPaidAmount(MockDataValues.contractId,MockDataValues.amount);
        putCall(url, HttpStatus.OK,"id",MockDataValues.gatewayOrderId);
        mapFromJson(Object.class);
    }

    @Test
    public void paymentFailed() {
    }

    @Test
    void testPaymentFailed() {
    }
}
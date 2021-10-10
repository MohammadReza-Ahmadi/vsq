package com.vosouq.bookkeeping.mockmvc;

import com.vosouq.bookkeeping.service.business.thirdparty.ContractRepository;
import com.vosouq.bookkeeping.util.AppUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("dev")
@SpringBootTest
@AutoConfigureMockMvc
public class MVC_BusinessIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ContractRepository contractRepository;

    @Test
    void recharge_account_and_create_contract() throws Exception {
        long amount = 10000000L;
        long contractId = 123L;
        long sellerUserId = 100L;

        // 1- charge account
        String gatewayOrderId = account_recharge(amount);
        contract_payment_success(gatewayOrderId);

//         2- create contract
//        contract_create(contractId);

        // 3- payment-1
//        for(int i=0; i < 1; i++) {
//            String gatewayOrderId = contract_payment(contractId);
//            contract_payment_success(gatewayOrderId);
//        }

//        // 4- delivery and settlement of contract
//        contract_delivery_and_settlement(contractId,sellerUserId);
    }


    @Test
    public String account_recharge(Long amount) throws Exception {
        String url = "/accounts/recharge/" + amount;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(url).contentType(MediaType.APPLICATION_JSON)).andReturn();
        if (AppUtil.isNotNull(mvcResult.getResolvedException())) {
            throw mvcResult.getResolvedException();
        }
        assertEquals(200, mvcResult.getResponse().getStatus());
        System.out.println("PWG url is: "+mvcResult.getResponse().getContentAsString());
        return mvcResult.getResponse().getContentAsString().split("http://192.168.88.235:8013/ipg/mellat/")[1].split("/")[0];
    }

    @Test
    public void contract_create(Long contractId) throws Exception {
        String url = "/contracts/" + contractId + "/create";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON)).andReturn();
        if (AppUtil.isNotNull(mvcResult.getResolvedException())) {
            throw mvcResult.getResolvedException();
        }
        assertEquals(201, mvcResult.getResponse().getStatus());
    }

    @Test
    public String contract_payment(Long contractId) throws Exception {
        String url = "/contracts/" + contractId + "/ipg/payment";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON)).andReturn();
        if (AppUtil.isNotNull(mvcResult.getResolvedException())) {
            throw mvcResult.getResolvedException();
        }
        assertEquals(201, mvcResult.getResponse().getStatus());
        return mvcResult.getResponse().getContentAsString().split("http://192.168.88.235:8013/ipg/mellat/")[1].split("/")[0];
    }

    private void contract_payment_success(String gatewayOrderId) throws Exception {
        String url = "/ipg/payment-success";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", gatewayOrderId)
        ).andReturn();

        if (AppUtil.isNotNull(mvcResult.getResolvedException())) {
            throw mvcResult.getResolvedException();
        }
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

}
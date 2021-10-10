package com.vosouq.bookkeeping.controller.business.old;

import com.vosouq.bookkeeping.controller.dto.responses.ContractGetAmountResponse;
import com.vosouq.bookkeeping.controller.MockDataValues;
import com.vosouq.bookkeeping.service.business.UserBusinessService;
import com.vosouq.bookkeeping.service.business.thirdparty.ContractRepository;
import com.vosouq.bookkeeping.util.AppUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("dev")
@SpringBootTest
@AutoConfigureMockMvc
public class ContractBusinessControllerTest_OLD {

    private final String baseUrlMapping = "/contracts";


    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserBusinessService userBusinessService;
    @MockBean
    private ContractRepository contractRepository;

    public void setMvc(MockMvc mvc) {
        this.mvc = mvc;
    }

    public void setUserBusinessService(UserBusinessService userBusinessService) {
        this.userBusinessService = userBusinessService;
    }

    public void setContractRepository(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Test
    void create() throws Exception {
        long specUserId = MockDataValues.userId;
        long specContractId = MockDataValues.contractId;
        long specAmount = MockDataValues.amount;

        String url = baseUrlMapping + "/" + specContractId  + "/create/";

        Mockito.when(contractRepository.getContractPaymentAmount(specContractId)).thenReturn(new ContractGetAmountResponse(specAmount));
        Mockito.when(userBusinessService.getCurrentUserId()).thenReturn(specUserId);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON)).andReturn();
        if (AppUtil.isNotNull(mvcResult.getResolvedException())) {
            throw mvcResult.getResolvedException();
        }
        assertEquals(204, mvcResult.getResponse().getStatus());
    }

    @Test
    public void ipgPaymentRequest_spec_contractId_spec_Amount() throws Exception {
        long specUserId = MockDataValues.userId;
        long specContractId = MockDataValues.contractId;
        long specAmount = MockDataValues.amount;

        String url = baseUrlMapping + "/" + specContractId  + "/ipg/payment/";

        Mockito.when(contractRepository.getContractPaymentAmount(specContractId)).thenReturn(new ContractGetAmountResponse(specAmount));
        Mockito.when(userBusinessService.getCurrentUserId()).thenReturn(specUserId);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON)).andReturn();
        if (AppUtil.isNotNull(mvcResult.getResolvedException())) {
            throw mvcResult.getResolvedException();
        }
        System.out.println(">>>>>>>>>>>>> url= "+mvcResult.getResponse().getContentAsString());
        MockDataValues.gatewayOrderId = mvcResult.getResponse().getContentAsString().split("http://192.168.88.235:8013/ipg/mellat/")[1].split("/")[0];
        assertEquals(201, mvcResult.getResponse().getStatus());
    }

    @Test
    void accountPayment() throws Exception {
        long specUserId = MockDataValues.userId;
        long specContractId = MockDataValues.contractId;
        long specAmount = MockDataValues.amount;

        String url = baseUrlMapping + "/" + specContractId  + "/account/payment";

        Mockito.when(contractRepository.getContractPaymentAmount(specContractId)).thenReturn(new ContractGetAmountResponse(specAmount));
        Mockito.doNothing().when(contractRepository).informContractBy‌PaidAmount(specContractId, specAmount);
        Mockito.when(userBusinessService.getCurrentUserId()).thenReturn(specUserId);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(url).contentType(MediaType.APPLICATION_JSON)).andReturn();
        if (AppUtil.isNotNull(mvcResult.getResolvedException())) {
            throw mvcResult.getResolvedException();
        }
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void goodsDelivery() throws Exception {
        long specUserId = MockDataValues.userId;
        long specContractId = MockDataValues.contractId;
        long specAmount = MockDataValues.amount;

        String url = baseUrlMapping + "/" + specContractId  + "/delivery/";

        Mockito.when(contractRepository.getContractPaymentAmount(specContractId)).thenReturn(new ContractGetAmountResponse(specAmount));
        Mockito.when(userBusinessService.getCurrentUserId()).thenReturn(specUserId);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(url).contentType(MediaType.APPLICATION_JSON)).andReturn();
        if (AppUtil.isNotNull(mvcResult.getResolvedException())) {
            throw mvcResult.getResolvedException();
        }
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    void settlement() throws Exception {
        long specUserId = MockDataValues.userId;
        long specSellerUserId = MockDataValues.sellerUserId;
        long specContractId = MockDataValues.contractId;
        long specAmount = MockDataValues.amount;

        String url = baseUrlMapping + "/" + specContractId  + "/settlement/" + specSellerUserId;

        Mockito.when(contractRepository.getContractPaymentAmount(specContractId)).thenReturn(new ContractGetAmountResponse(specAmount));
        Mockito.when(userBusinessService.getCurrentUserId()).thenReturn(specUserId);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(url).contentType(MediaType.APPLICATION_JSON)).andReturn();
        if (AppUtil.isNotNull(mvcResult.getResolvedException())) {
            throw mvcResult.getResolvedException();
        }
        assertEquals(200, mvcResult.getResponse().getStatus());
    }



}
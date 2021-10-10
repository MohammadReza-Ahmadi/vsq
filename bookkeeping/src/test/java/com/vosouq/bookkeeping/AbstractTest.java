package com.vosouq.bookkeeping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vosouq.bookkeeping.controller.MockDataValues;
import com.vosouq.bookkeeping.controller.dto.requests.GetIpgPaymentRefIdRequest;
import com.vosouq.bookkeeping.controller.dto.responses.ContractGetAmountResponse;
import com.vosouq.bookkeeping.controller.dto.responses.IpgResponse;
import com.vosouq.bookkeeping.exception.VosouqBadRequestException;
import com.vosouq.bookkeeping.service.business.UserBusinessService;
import com.vosouq.bookkeeping.service.business.thirdparty.ContractRepository;
import com.vosouq.bookkeeping.service.business.thirdparty.PaymentGatewayRepository;
import com.vosouq.bookkeeping.service.crud.BookkeepingRequestCrudService;
import com.vosouq.bookkeeping.util.AppUtil;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@ActiveProfiles("dev")
@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractTest {

    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected BookkeepingRequestCrudService bookkeepingRequestCrudService;
    @MockBean
    protected UserBusinessService userBusinessService;
    @MockBean
    private ContractRepository contractRepository;
//    @MockBean
    private PaymentGatewayRepository paymentGatewayRepository;

    private MvcResult mvcResult;

    protected void mockUserBusinessService() {
        mockUserBusinessService(MockDataValues.userId);
    }

    protected void mockUserBusinessService(Long userId) {
        Mockito.when(userBusinessService.getCurrentUserId()).thenReturn(userId);
    }

    protected void mockContractRepository() {
        Mockito.when(contractRepository.getContractPaymentAmount(MockDataValues.contractId)).thenReturn(new ContractGetAmountResponse(MockDataValues.amount));
    }

    protected void doNothingWhenCallInformContractByPaidAmount(long contractId, long amount) {
        Mockito.doNothing().when(contractRepository).informContractBy‌PaidAmount(contractId, amount);
    }

    protected void mockContractRepository(Long contractId, Long amount) {
        Mockito.when(contractRepository.getContractPaymentAmount(contractId)).thenReturn(new ContractGetAmountResponse(amount));
    }

    protected void mockPaymentGatewayRepository(IpgResponse ipgResponse) {
        Mockito.when(paymentGatewayRepository.getIpgPaymentRequestRefId(new GetIpgPaymentRefIdRequest(MockDataValues.userId, BigDecimal.valueOf(MockDataValues.amount)))).thenReturn(ipgResponse);
    }

    protected MvcResult getCall(String endpointUrl) throws Exception {
        return getCall(endpointUrl,HttpStatus.OK);
    }

    protected MvcResult getCall(String endpointUrl, HttpStatus expectedStatus) throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(endpointUrl).contentType(MediaType.APPLICATION_JSON)).andReturn();
        checkExceptions(mvcResult);
        assertEquals(expectedStatus.value(), mvcResult.getResponse().getStatus());
        this.mvcResult = mvcResult;
        return mvcResult;
    }

    protected MvcResult postCall(String endpointUrl) throws Exception {
        return postCall(endpointUrl,HttpStatus.OK);
    }

    protected MvcResult postCall(String endpointUrl, HttpStatus expectedStatus) throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(endpointUrl).contentType(MediaType.APPLICATION_JSON)).andReturn();
        checkExceptions(mvcResult);
        assertEquals(expectedStatus.value(), mvcResult.getResponse().getStatus());
        this.mvcResult = mvcResult;
        return mvcResult;
    }

    protected MvcResult putCall(String endpointUrl) throws Exception {
        return putCall(endpointUrl,HttpStatus.OK);
    }

    protected MvcResult putCall(String endpointUrl, HttpStatus expectedStatus) throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(endpointUrl).contentType(MediaType.APPLICATION_JSON)).andReturn();
        checkExceptions(mvcResult);
        assertEquals(expectedStatus.value(), mvcResult.getResponse().getStatus());
        this.mvcResult = mvcResult;
        return mvcResult;
    }

    protected MvcResult putCall(String endpointUrl, HttpStatus expectedStatus,String requestParam, String value) throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(endpointUrl).
                contentType(MediaType.APPLICATION_JSON)
                .param(requestParam, value)
        ).andReturn();
        checkExceptions(mvcResult);
        assertEquals(expectedStatus.value(), mvcResult.getResponse().getStatus());
        this.mvcResult = mvcResult;
        return mvcResult;
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(Class<T> clazz) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = this.mvcResult.getResponse().getContentAsString();
        System.out.println("::::::::: result content is :::::::::");
        if (AppUtil.isNullOrEmpty(json))
            return clazz.getDeclaredConstructor().newInstance();
        return objectMapper.readValue(json, clazz);
    }

    protected <T> List<T> mapFromJsonToList(Class<T> clazz) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = this.mvcResult.getResponse().getContentAsString();
        System.out.println("::::::::: result content is :::::::::");
        if (AppUtil.isNullOrEmpty(json))
            return AppUtil.getEmptyList();

        return new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<T>>() {});
    }

    protected <T> T mapFromJson(MvcResult mvcResult, Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = mvcResult.getResponse().getContentAsString();
        return objectMapper.readValue(json, clazz);
    }

    protected void printResult(Object result){
        System.out.println(result);
    }

    protected void printResultList(List result){
        result.forEach(System.out::println);
    }

    private void checkExceptions(MvcResult mvcResult) throws Exception {
        if (mvcResult.getResponse().getStatus() == HttpServletResponse.SC_BAD_REQUEST) {
            throw new VosouqBadRequestException();
        }

        if (AppUtil.isNotNull(mvcResult.getResolvedException())) {
            throw mvcResult.getResolvedException();
        }
    }
}
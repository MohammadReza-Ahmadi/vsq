package com.vosouq.bookkeeping.controller.business.old;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vosouq.bookkeeping.constant.NumberConstants;
import com.vosouq.bookkeeping.controller.dto.responses.AccountBalanceResponse;
import com.vosouq.bookkeeping.controller.dto.responses.AccountTurnoverResponse;
import com.vosouq.bookkeeping.controller.MockDataValues;
import com.vosouq.bookkeeping.enumeration.RequestStatus;
import com.vosouq.bookkeeping.enumeration.RequestType;
import com.vosouq.bookkeeping.model.BookkeepingRequest;
import com.vosouq.bookkeeping.service.business.UserBusinessService;
import com.vosouq.bookkeeping.service.crud.BookkeepingRequestCrudService;
import com.vosouq.bookkeeping.util.AppUtil;
import org.jeasy.random.randomizers.range.LongRangeRandomizer;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@ActiveProfiles("dev")
@SpringBootTest
@AutoConfigureMockMvc
public class AccountBusinessControllerTest_OLD {

    private final String baseUrlMapping = "/accounts";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookkeepingRequestCrudService bookkeepingRequestCrudService;

    @MockBean
    private UserBusinessService userBusinessService;

    public void setMvc(MockMvc mvc) {
        this.mvc = mvc;
    }

    public void setBookkeepingRequestCrudService(BookkeepingRequestCrudService bookkeepingRequestCrudService) {
        this.bookkeepingRequestCrudService = bookkeepingRequestCrudService;
    }

    public void setUserBusinessService(UserBusinessService userBusinessService) {
        this.userBusinessService = userBusinessService;
    }

    @Test
    void ipgRecharge() {
    }

    @Test
    void getWithdrawableBalance() {
    }

    @Test
    void getTurnovers() {
    }

    @Test
    public void ipgRecharge_specUserSpecAmount() throws Exception {
        /* get spec amount */
        BigDecimal rechargeAmount = BigDecimal.valueOf(MockDataValues.rechargeAmount);
        /* get spec user */
        Long userId = MockDataValues.userId;

        String url = baseUrlMapping + "/recharge/" + rechargeAmount;
        Mockito.when(userBusinessService.getCurrentUserId()).thenReturn(userId);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(url).contentType(MediaType.APPLICATION_JSON)).andReturn();
        if (AppUtil.isNotNull(mvcResult.getResolvedException())) {
            throw mvcResult.getResolvedException();
        }
        assertEquals(200, mvcResult.getResponse().getStatus());
        System.out.println(">>>>>>>>>>>>> url= "+mvcResult.getResponse().getContentAsString());


        /* checking result */
        Long gatewayOrderId = Long.valueOf(mvcResult.getResponse().getContentAsString().split("http://192.168.88.235:8013/ipg/mellat/")[1].split("/")[0]);
        BookkeepingRequest bookkeepingRequest = bookkeepingRequestCrudService.findByGatewayOrderId(gatewayOrderId);
        assertEquals(gatewayOrderId,bookkeepingRequest.getGatewayOrderId());
        assertEquals(userId,bookkeepingRequest.getUserId());
        assertEquals(userId,bookkeepingRequest.getRequesterId());
        assertEquals(rechargeAmount,bookkeepingRequest.getAmount().setScale(NumberConstants.ZERO_INT, RoundingMode.HALF_UP));
        assertEquals(BigDecimal.ZERO, bookkeepingRequest.getCommissionAmount().setScale(NumberConstants.ZERO_INT, RoundingMode.HALF_UP));
        assertEquals(BigDecimal.ZERO, bookkeepingRequest.getVatAmount().setScale(NumberConstants.ZERO_INT, RoundingMode.HALF_UP));
        assertEquals(RequestType.ACCOUNT_RECHARGE, bookkeepingRequest.getRequestType());
        assertEquals(RequestStatus.PENDING, bookkeepingRequest.getRequestStatus());
        MockDataValues.gatewayOrderId = gatewayOrderId.toString();
    }


    @Test
    public void ipgRechargeFirstUser() throws Exception {
        /* generate random amount */
        BigDecimal amount = BigDecimal.valueOf(new LongRangeRandomizer(10000L, 10000000L).getRandomValue());

        /* generate random userId */
        Long userId = new LongRangeRandomizer(1L, 101L).getRandomValue();

        String url = baseUrlMapping + "/recharge/" + amount;
        Mockito.when(userBusinessService.getCurrentUserId()).thenReturn(userId);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(url).contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());

        /* checking result */
        Long gatewayOrderId = Long.valueOf(mvcResult.getResponse().getContentAsString().split("http://192.168.88.235:8013/ipg/mellat/")[1].split("/")[0]);
        BookkeepingRequest bookkeepingRequest = bookkeepingRequestCrudService.findByGatewayOrderId(gatewayOrderId);
        assertEquals(gatewayOrderId,bookkeepingRequest.getGatewayOrderId());
        assertEquals(userId,bookkeepingRequest.getUserId());
        assertEquals(userId,bookkeepingRequest.getRequesterId());
        assertEquals(amount,bookkeepingRequest.getAmount().setScale(NumberConstants.ZERO_INT, RoundingMode.HALF_UP));
        assertEquals(BigDecimal.ZERO, bookkeepingRequest.getCommissionAmount().setScale(NumberConstants.ZERO_INT, RoundingMode.HALF_UP));
        assertEquals(BigDecimal.ZERO, bookkeepingRequest.getVatAmount().setScale(NumberConstants.ZERO_INT, RoundingMode.HALF_UP));
        assertEquals(RequestType.ACCOUNT_RECHARGE, bookkeepingRequest.getRequestType());
        assertEquals(RequestStatus.PENDING, bookkeepingRequest.getRequestStatus());
    }



    @Test
    public void ipgRechargeSpecificUser() throws Exception {
        /* generate random amount */
        BigDecimal amount = BigDecimal.valueOf(new LongRangeRandomizer(10000L, 10000000L).getRandomValue());

        /* generate random userId */
        Long userId = MockDataValues.userId;

        String url = baseUrlMapping + "/recharge/" + amount;
        Mockito.when(userBusinessService.getCurrentUserId()).thenReturn(userId);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(url).contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
        System.out.println(">>>>>>>>>>>>> url= "+mvcResult.getResponse().getContentAsString());


        /* checking result */
        Long gatewayOrderId = Long.valueOf(mvcResult.getResponse().getContentAsString().split("http://192.168.88.235:8013/ipg/mellat/")[1].split("/")[0]);
        BookkeepingRequest bookkeepingRequest = bookkeepingRequestCrudService.findByGatewayOrderId(gatewayOrderId);
        assertEquals(gatewayOrderId,bookkeepingRequest.getGatewayOrderId());
        assertEquals(userId,bookkeepingRequest.getUserId());
        assertEquals(userId,bookkeepingRequest.getRequesterId());
        assertEquals(amount,bookkeepingRequest.getAmount().setScale(NumberConstants.ZERO_INT, RoundingMode.HALF_UP));
        assertEquals(BigDecimal.ZERO, bookkeepingRequest.getCommissionAmount().setScale(NumberConstants.ZERO_INT, RoundingMode.HALF_UP));
        assertEquals(BigDecimal.ZERO, bookkeepingRequest.getVatAmount().setScale(NumberConstants.ZERO_INT, RoundingMode.HALF_UP));
        assertEquals(RequestType.ACCOUNT_RECHARGE, bookkeepingRequest.getRequestType());
        assertEquals(RequestStatus.PENDING, bookkeepingRequest.getRequestStatus());
    }

    @Test
    public void getBalanceOfFirstUser() throws Exception {
        String url = baseUrlMapping + "/balance/";

        /* generate random userId and return that */
        Long userId = new LongRangeRandomizer(1L, 101L).getRandomValue();
        Mockito.when(userBusinessService.getCurrentUserId()).thenReturn(userId);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());

        /* checking result */
        Long balance = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), AccountBalanceResponse.class).getBalance();
        assertEquals(NumberConstants.ZERO_LONG,balance);
    }

    @Test
    public void getBalanceOfSpecificUser() throws Exception {
        String url = baseUrlMapping + "/balance/";

        /* generate random userId and return that */
        Long userId = MockDataValues.userId;
        Mockito.when(userBusinessService.getCurrentUserId()).thenReturn(userId);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());

        /* checking result */
        Long balance = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), AccountBalanceResponse.class).getBalance();
        System.out.println(">>>>>>>>>>> balance="+balance);
//        assertEquals(NumberConstants.ZERO_LONG,balance);
    }

    @Test
    public void getDepositTurnoversOfFirstUser() throws Exception {
        String url = baseUrlMapping + "/turnovers/DEPOSIT";

        /* generate random userId and return that */
        Long userId = new LongRangeRandomizer(1L, 101L).getRandomValue();
        Mockito.when(userBusinessService.getCurrentUserId()).thenReturn(userId);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());

        /* checking result */
        List<AccountTurnoverResponse> turnoverResponses = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertEquals(NumberConstants.ZERO_INT,turnoverResponses.size());
    }

    @Test
    public void getWithdrawalTurnoversOfFirstUser() throws Exception {
        String url = baseUrlMapping + "/turnovers/WITHDRAWAL";

        /* generate random userId and return that */
        Long userId = new LongRangeRandomizer(1L, 101L).getRandomValue();
        Mockito.when(userBusinessService.getCurrentUserId()).thenReturn(userId);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());

        /* checking result */
        List<AccountTurnoverResponse> turnoverResponses = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), List.class);
        assertEquals(NumberConstants.ZERO_INT,turnoverResponses.size());
    }

    @Test
    public void getDepositTurnoversOfSpecificUser() throws Exception {
        String url = baseUrlMapping + "/turnovers/DEPOSIT";

        /* generate random userId and return that */
        Long userId = MockDataValues.userId;
        Mockito.when(userBusinessService.getCurrentUserId()).thenReturn(userId);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON)).andReturn();
        if (AppUtil.isNotNull(mvcResult.getResolvedException())) {
            throw mvcResult.getResolvedException();
        }
        assertEquals(200, mvcResult.getResponse().getStatus());

        /* checking result */
        List<AccountTurnoverResponse> turnoverResponses = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), List.class);
        System.out.println(">>>>>>>>>> depositTurnoversSize="+turnoverResponses.size());
//        assertEquals(NumberConstants.ZERO_INT,turnoverResponses.size());
    }

    @Test
    public void getWithdrawalTurnoversOfSpecificUser() throws Exception {
        String url = baseUrlMapping + "/turnovers/WITHDRAWAL";

        /* generate random userId and return that */
        Long userId = MockDataValues.userId;
        Mockito.when(userBusinessService.getCurrentUserId()).thenReturn(userId);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());

        /* checking result */
        List turnoverResponses = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), List.class);
/*        Map<String,Object> map = (Map<String, Object>) turnoverResponses.iterator().next();
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            System.out.println(next.getKey()+", "+ next.getValue());
        }*/
        System.out.println(">>>>>>>>>> withdrawalTurnoversSize="+turnoverResponses.size());
//        assertEquals(NumberConstants.ZERO_INT,turnoverResponses.size());
    }

    @Test
    void withdrawalAccountAndDepositToIban() throws Exception {
        long userId = MockDataValues.sellerUserId;
        long amount = MockDataValues.seller_withdrawable_balance;
        String iban = MockDataValues.iban;

        String url = baseUrlMapping + "/" + iban  + "/withdrawal/"+amount;

        Mockito.when(userBusinessService.getCurrentUserId()).thenReturn(userId);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(url).contentType(MediaType.APPLICATION_JSON)).andReturn();
        if (AppUtil.isNotNull(mvcResult.getResolvedException())) {
            throw mvcResult.getResolvedException();
        }
        assertEquals(200, mvcResult.getResponse().getStatus());
    }
}
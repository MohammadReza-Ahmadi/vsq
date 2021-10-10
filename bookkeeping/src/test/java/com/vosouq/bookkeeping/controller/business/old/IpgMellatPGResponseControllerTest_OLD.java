package com.vosouq.bookkeeping.controller.business.old;

import com.vosouq.bookkeeping.controller.MockDataValues;
import com.vosouq.bookkeeping.controller.dto.responses.ContractGetAmountResponse;
import com.vosouq.bookkeeping.service.business.UserBusinessService;
import com.vosouq.bookkeeping.service.business.thirdparty.ContractRepository;
import com.vosouq.bookkeeping.service.crud.BookkeepingRequestCrudService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@ActiveProfiles("dev")
@SpringBootTest
@AutoConfigureMockMvc
public class IpgMellatPGResponseControllerTest_OLD {

    private final String baseUrlMapping = "/ipg";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookkeepingRequestCrudService bookkeepingRequestCrudService;

    @MockBean
    private UserBusinessService userBusinessService;
    @MockBean
    private ContractRepository contractRepository;

    public void setMvc(MockMvc mvc) {
        this.mvc = mvc;
    }

    public void setBookkeepingRequestCrudService(BookkeepingRequestCrudService bookkeepingRequestCrudService) {
        this.bookkeepingRequestCrudService = bookkeepingRequestCrudService;
    }

    public void setUserBusinessService(UserBusinessService userBusinessService) {
        this.userBusinessService = userBusinessService;
    }

    public void setContractRepository(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Test
    public void paymentSuccess_by_spec_user() throws Exception {
        String url = baseUrlMapping + "/payment-success";
        long specContractId = MockDataValues.contractId;
        long specAmount = MockDataValues.amount;
        String specGatewayOrderId = MockDataValues.gatewayOrderId;

        Mockito.when(contractRepository.getContractPaymentAmount(specContractId)).thenReturn(new ContractGetAmountResponse(specAmount));
        Mockito.doNothing().when(contractRepository).informContractBy‌PaidAmount(specContractId, specAmount);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", specGatewayOrderId)
        ).andReturn();

        if (AppUtil.isNotNull(mvcResult.getResolvedException())) {
            throw mvcResult.getResolvedException();
        }
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void paymentFailed() {
    }

    @Test
    void paymentSuccess() {
    }

    @Test
    void testPaymentFailed() {
    }
}
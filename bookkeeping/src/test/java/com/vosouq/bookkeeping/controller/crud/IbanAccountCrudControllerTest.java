package com.vosouq.bookkeeping.controller.crud;

import com.vosouq.bookkeeping.controller.MockDataValues;
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
public class IbanAccountCrudControllerTest {
    private final String baseUrlMapping = "/iban-accounts";

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
    public void create() throws Exception {
        long userId = MockDataValues.userId;

        String url = baseUrlMapping;

        Mockito.when(userBusinessService.getCurrentUserId()).thenReturn(userId);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "    \"iban\":\"IR930120010000001436505019\"," +
                        "    \"bankName\":\"Mellat\"" +
                        "}")
                .accept(MediaType.APPLICATION_JSON)
        ).andReturn();

        if (AppUtil.isNotNull(mvcResult.getResolvedException())) {
            throw mvcResult.getResolvedException();
        }
        assertEquals(204, mvcResult.getResponse().getStatus());
    }

    @Test
    public void getIBANs() throws Exception {
        long userId = MockDataValues.userId;

        String url = baseUrlMapping + "/list";

        Mockito.when(userBusinessService.getCurrentUserId()).thenReturn(userId);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON)).andReturn();

        if (AppUtil.isNotNull(mvcResult.getResolvedException())) {
            throw mvcResult.getResolvedException();
        }
        assertEquals(200, mvcResult.getResponse().getStatus());
        System.out.println(mvcResult.getResponse().getContentAsString());
    }
}
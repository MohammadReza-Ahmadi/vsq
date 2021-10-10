package com.vosouq.bookkeeping.controller.crud;

import com.vosouq.bookkeeping.AbstractTest;
import com.vosouq.bookkeeping.controller.dto.requests.IbanAccountRequest;
import com.vosouq.bookkeeping.exception.InvalidParameterException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("dev")
@SpringBootTest
@AutoConfigureMockMvc
public class _2_MockMvc_IbanAccountCrudControllerTest extends AbstractTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testCallShouldReturnDefaultMessage() throws Exception {
        this.mvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello, World")));
    }

    @Test
    void create_success() throws Exception {
        String uri = "/iban-accounts/";
        IbanAccountRequest product = new IbanAccountRequest();
        product.setIban("IR930120010000001436505019");
        product.setBankName("Saderat");

        String inputJson = super.mapToJson(product);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(201, status);
        String content = mvcResult.getResponse().getContentAsString();
    }

    @Test
    void create_failed_incorrect_length() throws Exception {
        String uri = "/iban-accounts/";
        IbanAccountRequest product = new IbanAccountRequest();
        product.setIban("IR9301200100000014365050190");
        product.setBankName("Saderat");

        String inputJson = super.mapToJson(product);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        assertTrue(mvcResult.getResolvedException() instanceof InvalidParameterException);
        assertEquals(((InvalidParameterException) mvcResult.getResolvedException()).getParameters()[0],
                "The iban length is not correct!");
    }
}

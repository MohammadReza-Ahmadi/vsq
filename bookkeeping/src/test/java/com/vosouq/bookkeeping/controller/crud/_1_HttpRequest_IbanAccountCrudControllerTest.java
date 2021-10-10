package com.vosouq.bookkeeping.controller.crud;

import com.vosouq.bookkeeping.controller.dto.requests.IbanAccountRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class _1_HttpRequest_IbanAccountCrudControllerTest{

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCallShouldReturnDefaultMessage() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/iban-accounts/test-call",
                String.class)).contains("Hello, World");
    }

    @Test
    void create() throws Exception {
        String url = "/iban-accounts/";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);

//        JSONObject personJsonObject = new JSONObject();
//        personJsonObject.put("id", 1);
//        personJsonObject.put("name", "John");

        IbanAccountRequest ibanRequest = new IbanAccountRequest();
        ibanRequest.setIban("IR930120010000001436505019");
        ibanRequest.setBankName("Saderat");

        HttpEntity<IbanAccountRequest> request = new HttpEntity<>(ibanRequest);
        ResponseEntity<IbanAccountRequest> response = restTemplate
                .exchange(url, HttpMethod.POST, request, IbanAccountRequest.class);
        assertEquals(201, response.getStatusCode().value());
    }
}
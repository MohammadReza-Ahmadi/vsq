package com.vosouq.bookkeeping.resttemplate;

import com.vosouq.bookkeeping.controller.dto.requests.IbanAccountRequest;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.randomizers.range.IntegerRangeRandomizer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RST_AccountBusinessControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void create() throws Exception {
        int count =1;
        EasyRandom generator = new EasyRandom();
//        List<BigInteger> amounts = generator.objects(BigInteger.class, 5)
//                .collect(Collectors.toList());
        for (int i=0; i < count; i++) {
            Integer amount = new IntegerRangeRandomizer(1000,8000).getRandomValue();
            System.out.println("amount: " + amount);
            String url = "/accounts/recharge/" + amount;
            ResponseEntity<IbanAccountRequest> response = restTemplate
                    .exchange(url, HttpMethod.PUT, null, IbanAccountRequest.class);
            assertEquals(200, response.getStatusCode().value());
        }
    }
}
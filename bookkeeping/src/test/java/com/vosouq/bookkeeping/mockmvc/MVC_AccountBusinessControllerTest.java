package com.vosouq.bookkeeping.mockmvc;

import com.vosouq.bookkeeping.service.business.UserBusinessService;
import org.jeasy.random.randomizers.range.IntegerRangeRandomizer;
import org.jeasy.random.randomizers.range.LongRangeRandomizer;
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
public class MVC_AccountBusinessControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserBusinessService userBusinessService;

    @Test
    public void testRandomizeNumber() {
        for (int i = 1; i <= 100; i++)
            System.out.println("--------------->> " + new LongRangeRandomizer(1L, 11L).getRandomValue());
    }

    @Test
    void recharge_1000() throws Exception {
        String url = "/accounts/recharge/1000";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(url).contentType(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
    }

    @Test
    public void recharge_by_multi_random_amounts() throws Exception {
        int count = 10;
//        EasyRandom generator = new EasyRandom();
//        List<BigInteger> amounts = generator.objects(BigInteger.class, 5)
//                .collect(Collectors.toList());
        for (int i = 0; i < count; i++) {
            Integer amount = new IntegerRangeRandomizer(1000, 8000).getRandomValue();
            System.out.println("amount: " + amount);
            String url = "/accounts/recharge/" + amount;

            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(url).contentType(MediaType.APPLICATION_JSON)).andReturn();
            assertEquals(200, mvcResult.getResponse().getStatus());
        }
    }

}
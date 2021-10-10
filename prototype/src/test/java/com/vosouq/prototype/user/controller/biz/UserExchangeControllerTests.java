package com.vosouq.prototype.user.controller.biz;

import com.vosouq.commons.model.Gender;
import com.vosouq.prototype.user.model.User;
import com.vosouq.prototype.user.repository.UserRepository;
import com.vosouq.prototype.user.service.UserService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserExchangeControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository userRepository;

//    private UserService userService;

    @Before
    public void setup() {
        User user = User.with()
                .uuid(UUID.randomUUID().toString())
                .email("pedram.frce@gmail.com")
                .address("Tehran, Iran")
                .income(150)
                .age(3)
                .firstName("pedro")
                .lastName("ferrari")
                .gender(Gender.FEMALE)
                .phoneNumber("9357693692")
//                .createDate(new Date())
//                .updateDate(new Date())
            ;

//        Mockito.when(userService.getAll()).thenReturn(Arrays.asList(user));
        Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user));
    }

    @Test
    public void addRate_whenUserIsMock() throws Exception {
        ResultActions resultActions = mvc
                .perform(MockMvcRequestBuilders.get("/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        resultActions.andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", Matchers.is("pedro")));
    }

}

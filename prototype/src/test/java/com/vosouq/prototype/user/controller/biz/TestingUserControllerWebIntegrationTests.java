package com.vosouq.prototype.user.controller.biz;

import com.vosouq.prototype.user.model.User;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestingUserControllerWebIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void testHomeController() {

        ResponseEntity<User> response = restTemplate.getForEntity("http://localhost:" + port + "/api/v1/tests/users/29873203-1c53-4497-b9e5-85229ee9e4a5", User.class);

        MatcherAssert.assertThat(response.getStatusCode(), Matchers.equalTo(HttpStatus.OK));

        MatcherAssert.assertThat(response.getBody(), Matchers.hasProperty("uuid"));

    }

}

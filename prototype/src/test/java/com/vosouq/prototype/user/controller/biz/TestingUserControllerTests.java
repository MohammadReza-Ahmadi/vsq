package com.vosouq.prototype.user.controller.biz;

import com.vosouq.prototype.user.model.User;
import com.vosouq.prototype.user.service.UserService;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

public class TestingUserControllerTests {

    @InjectMocks
    private TestingUserController testingUserController;

    @Mock
    private UserService userService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHomeController() {

        Mockito.when(userService.get("29873203-1c53-4497-b9e5-85229ee9e4a5")).thenReturn(User.with().phoneNumber("+989357693690"));

        User user = testingUserController.get(UUID.fromString("29873203-1c53-4497-b9e5-85229ee9e4a5"));

        Mockito.verify(userService).get("29873203-1c53-4497-b9e5-85229ee9e4a5"); // just for verifying that get() method called one time

        Assert.assertEquals("+989357693690", user.getPhoneNumber());

        Assert.assertThat(user.getPhoneNumber(), Matchers.is("+989357693690"));

        MatcherAssert.assertThat(user.getPhoneNumber(), Matchers.is("+989357693690"));
    }

}

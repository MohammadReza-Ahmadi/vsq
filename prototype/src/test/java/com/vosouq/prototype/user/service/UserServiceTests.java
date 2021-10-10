package com.vosouq.prototype.user.service;

import com.vosouq.commons.exception.NotFoundException;
import com.vosouq.commons.exception.VosouqBaseException;
import com.vosouq.commons.model.Gender;
import com.vosouq.commons.util.NumberUtil;
import com.vosouq.prototype.user.model.User;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Test
    public void create_when_all_parameters_provided() {
        User user = userService.create(
                "Pedram_" + RandomString.make(4),
                "Farzaneh_" + RandomString.make(4),
                "+98912" + NumberUtil.generateRandomLong(7),
                Gender.MALE,
                NumberUtil.generateRandomInteger(2),
                NumberUtil.generateRandomInteger(6),
                "address_" + RandomString.make(25),
                "pedram_" + RandomString.make(4) + "@" + RandomString.make(5) + ".com");

        Assert.assertNotNull(user);
        Assert.assertNotNull(user.getUuid());
//        Assert.assertNotNull(user.getCreateDate());
//        Assert.assertNotNull(user.getUpdateDate());
//        MatcherAssert.assertThat(user.getCreateDate(), Matchers.lessThanOrEqualTo(new Date()));
    }

    @Test(expected = VosouqBaseException.class)
    public void create_when_phoneNumber_is_duplicate() {

        userService.create(
                "Pedram_" + RandomString.make(4),
                "Farzaneh_" + RandomString.make(4),
                "+989121429259",
                Gender.MALE,
                NumberUtil.generateRandomInteger(2),
                NumberUtil.generateRandomInteger(6),
                "address_" + RandomString.make(25),
                "pedram_" + RandomString.make(4) + "@" + RandomString.make(5) + ".com");

    }

    @Test
    public void delete() {
//        userService.delete("0d5ae041-1155-4540-93c9-f5f1e388725c");
    }

//    @Test(expected = NotFoundException.class)
    public void delete_when_uuid_not_exist() {
//        userService.delete("29873203-1c53-4497-b9e5-85229ee9e4a5");
    }

//    @Test(expected = NotFoundException.class)
    public void delete_when_uuid_is_incorrect() {
//        userService.delete("29873203-1c53-4497");
    }

//    @Test(expected = Exception.class)
    public void delete_when_uuid_is_null() {
//        userService.delete(null);
    }

    @Test
    public void update() {
    }

    @Test
    public void get() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void businessExceptionGenerator() {
    }
}

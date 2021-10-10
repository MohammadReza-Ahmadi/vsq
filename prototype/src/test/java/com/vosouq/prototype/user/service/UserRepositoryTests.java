package com.vosouq.prototype.user.service;

import com.vosouq.prototype.user.model.User;
import com.vosouq.prototype.user.repository.UserRepository;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void sample_test() {
        User user;


        Optional<User> userOptional = userRepository.findByPhoneNumber("+989127762101");


        userRepository.findAll().forEach(user1 -> {
            System.out.println(user1.getPhoneNumber());
        });


//        if (userOptional.isPresent()) {
//            user = userOptional.get();
//            Assert.assertNotNull(user);
//        }

        System.out.println("");
    }

}

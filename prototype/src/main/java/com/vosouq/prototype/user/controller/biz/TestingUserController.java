package com.vosouq.prototype.user.controller.biz;

import com.vosouq.commons.annotation.VosouqRestController;
import com.vosouq.prototype.user.model.User;
import com.vosouq.prototype.user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@VosouqRestController
public class TestingUserController {

    private final UserService userService;

    public TestingUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/tests/users/{uuid}")
    public User get(@PathVariable("uuid") UUID uuid) {
        return userService.get(uuid.toString());
    }

    @PostMapping("/tests/users")
    public User post(@RequestBody User user) {
        return userService.create(
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getGender(),
                user.getAge(),
                user.getIncome(),
                user.getAddress(),
                user.getEmail());
    }

}

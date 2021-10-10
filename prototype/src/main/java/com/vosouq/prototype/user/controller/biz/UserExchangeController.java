package com.vosouq.prototype.user.controller.biz;

import com.vosouq.commons.annotation.Created;
import com.vosouq.commons.annotation.VosouqRestController;
import com.vosouq.prototype.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.UUID;

@VosouqRestController
public class UserExchangeController {

    private UserService userService;

    @Autowired
    public UserExchangeController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/{uuid}/exchanges")
    @Created
    public void addRate(@PathVariable UUID uuid, @Valid @RequestBody UserExchangeRequest request) {
        userService.businessExceptionGenerator();
    }

}

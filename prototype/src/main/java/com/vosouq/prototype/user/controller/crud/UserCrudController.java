package com.vosouq.prototype.user.controller.crud;

import com.vosouq.commons.annotation.Created;
import com.vosouq.commons.annotation.NoContent;
import com.vosouq.commons.annotation.VosouqRestController;
import com.vosouq.prototype.user.model.User;
import com.vosouq.prototype.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@VosouqRestController
@RequestMapping("/users")
public class UserCrudController {

    private UserService userService;

    @Autowired
    public UserCrudController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Created
    public UserCrudResponse createUser(@Valid @RequestBody UserCreateRequest request) {

        User user = userService.create(
                request.getFirstName(),
                request.getLastName(),
                request.getPhoneNumber(),
                request.getGender(),
                request.getAge(),
                request.getIncome(),
                request.getAddress(),
                request.getEmail());

        return map(user);
    }

    @GetMapping("/{uuid}")
    public UserCrudResponse getUser(@PathVariable UUID uuid) {
        User user = userService.get(uuid.toString());
        return map(user);
    }

    @GetMapping
    public List<UserCrudResponse> getUsers() {

        List<User> users = userService.getAll();
        return users.stream().map(this::map).collect(Collectors.toList());
    }

    @PutMapping("/{uuid}")
    @NoContent
    public void updateUser(@Valid @PathVariable UUID uuid, @RequestBody UserUpdateRequest request) {

        userService.update(uuid.toString(),
                request.getFirstName(),
                request.getLastName(),
                request.getPhoneNumber(),
                request.getGender(),
                request.getAge(),
                request.getIncome(),
                request.getAddress(),
                request.getEmail());
    }

    @DeleteMapping("/{uuid}")
    @NoContent
    public void deleteUser(@Valid @PathVariable UUID uuid) {
        userService.delete(uuid.toString());
    }

    private UserCrudResponse map(User user) {
        UserCrudResponse response = new UserCrudResponse();
        response.setUuid(user.getUuid());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setGender(user.getGender());
//        response.setCreateDate(user.getCreateDate());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setUuid(user.getUuid());
        response.setAddress(user.getAddress());
        response.setAge(user.getAge());
        response.setIncome(user.getIncome());

        return response;
    }
}

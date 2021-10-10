package com.vosouq.prototype.user.service;

import com.vosouq.commons.model.Gender;
import com.vosouq.prototype.user.model.User;

import java.util.List;

public interface UserService {

    User create(String firstName,
                String lastName,
                String phoneNumber,
                Gender gender,
                Integer age,
                Integer income,
                String address,
                String email);

    void delete(String uuid);

    void update(String uuid,
                String firstName,
                String lastName,
                String phoneNumber,
                Gender gender,
                Integer age,
                Integer income,
                String address,
                String email);

    User get(String uuid);

    List<User> getAll();

    void businessExceptionGenerator();

}

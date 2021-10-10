package com.vosouq.prototype.user.service.impl;

import com.vosouq.commons.model.Gender;
import com.vosouq.prototype.user.exception.PhoneNumberAlreadyExistException;
import com.vosouq.prototype.user.exception.UserAlreadyExistException;
import com.vosouq.prototype.user.exception.UserNotFoundException;
import com.vosouq.prototype.user.exception.UserOperationException;
import com.vosouq.prototype.user.model.User;
import com.vosouq.prototype.user.repository.UserRepository;
import com.vosouq.prototype.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(String firstName,
                       String lastName,
                       String phoneNumber,
                       Gender gender,
                       Integer age,
                       Integer income,
                       String address,
                       String email) {

        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);

        if (userOptional.isPresent())
            throw new UserAlreadyExistException();

        User user = new User();
        user.setUuid(UUID.randomUUID().toString());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        user.setGender(gender);
        user.setAge(age);
        user.setIncome(income);
        user.setAddress(address);
        user.setEmail(email);
//        user.setCreateDate(new Date());
//        user.setUpdateDate(new Date());

        return userRepository.save(user);
    }

    @Override
    public void delete(String uuid) {
        Optional<User> userOptional = userRepository.findById(uuid);
        User user = userOptional.orElseThrow(UserNotFoundException::new);

        userRepository.delete(user);
    }

    @Override
    public void update(String uuid,
                       String firstName,
                       String lastName,
                       String phoneNumber,
                       Gender gender,
                       Integer age,
                       Integer income,
                       String address,
                       String email) {

        Optional<User> userOptional = userRepository.findById(uuid);
        User user = userOptional.orElseThrow(UserNotFoundException::new);

        if (!StringUtils.isEmpty(firstName))
            user.setFirstName(firstName);

        if (!StringUtils.isEmpty(lastName))
            user.setLastName(lastName);

        if (!StringUtils.isEmpty(phoneNumber)) {
            if (!phoneNumber.equals(user.getPhoneNumber())) {
                Optional<User> userByPhoneNumber = userRepository.findByPhoneNumber(phoneNumber);
                if (userByPhoneNumber.isPresent())
                    throw new PhoneNumberAlreadyExistException();
            }
            user.setPhoneNumber(phoneNumber);
        }

        if (gender != null)
            user.setGender(gender);

        if (age != null)
            user.setAge(age);

        if (income != null)
            user.setIncome(income);

        if (address != null)
            user.setAddress(address);

        if (email != null)
            user.setEmail(email);

        userRepository.save(user);

    }

    @Override
    public User get(String uuid) {
        Optional<User> userOptional = userRepository.findById(uuid);
        return userOptional.orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<User> getAll() {
//        return userRepository.findAll();
        return null;
    }

    @Override
    public void businessExceptionGenerator() {
        throw new UserOperationException();
    }
}

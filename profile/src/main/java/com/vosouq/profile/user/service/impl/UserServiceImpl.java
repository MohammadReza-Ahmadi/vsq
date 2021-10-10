package com.vosouq.profile.user.service.impl;

import com.vosouq.commons.model.BinaryDataHolder;
import com.vosouq.commons.util.PasswordUtil;
import com.vosouq.profile.user.exception.*;
import com.vosouq.profile.user.model.*;
import com.vosouq.profile.user.repository.UserRepository;
import com.vosouq.profile.user.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Value("${registration.otp.request-period-in-seconds}")
    private int otpRequestPeriodInSeconds;

    private final DeviceService deviceService;
    private final UserRepository userRepository;
    private final SessionService sessionService;
    private final LoginHistoryService loginHistoryService;
    private final RegistrationSmsService registrationSmsService;
    private final MessageService messageService;
    private final BlockedUserService blockedUserService;

    public UserServiceImpl(DeviceService deviceService,
                           UserRepository userRepository,
                           SessionService sessionService,
                           LoginHistoryService loginHistoryService,
                           RegistrationSmsService registrationSmsService,
                           MessageService messageService,
                           BlockedUserService blockedUserService) {

        this.deviceService = deviceService;
        this.userRepository = userRepository;
        this.sessionService = sessionService;
        this.loginHistoryService = loginHistoryService;
        this.registrationSmsService = registrationSmsService;
        this.messageService = messageService;
        this.blockedUserService = blockedUserService;
    }

    @Override
    public Registration register(String udid,
                                 String deviceName,
                                 String os,
                                 String osVersion,
                                 String appVersion,
                                 String phoneNumber) {

        throwExceptionIfUserBlocked(udid, phoneNumber);

        User user = register(phoneNumber);
        Device device = deviceService.create(user.getId(), udid, deviceName, os, osVersion, appVersion);
        BinaryDataHolder<RegistrationSms, Boolean> registrationSms = registrationSmsService.create(user.getId(), device.getId(), (otpRequestPeriodInSeconds * 1000L));

//        if (registrationSms.getSecondType())
//            messageService.sendSms(user.getPhoneNumber(), registrationSms.getFirstType().getCode(), LocaleConfig.getRequestLocale());

        return new Registration(device.getId(), (int) (registrationSms.getFirstType().getExpireDate().getTime() - System.currentTimeMillis()) / 1000);
    }

    @Override
    @Transactional(noRollbackFor = {SmsNotFoundException.class, TooManySmsCodeTryException.class})
    public User verify(Long deviceId, String smsCode) {

        Device device = deviceService.get(deviceId);
        User user = get(device.getUserId());

        throwExceptionIfUserBlocked(device.getUdid(), user.getPhoneNumber());

        try {
            registrationSmsService.evaluateSms(device.getId(), smsCode);
        } catch (TooManySmsCodeTryException exception) {
            log.error("user with phoneNumber: {} " +
                            "and on device: {} " +
                            "tried too many times to enter otp sms. ",
                    user.getPhoneNumber(),
                    device.getId());

            blockedUserService.blockUser(device.getUserId(), user.getPhoneNumber(), deviceId, device.getUdid());

            throwExceptionIfUserBlocked(device.getUdid(), user.getPhoneNumber());
        }

        return user;
    }

    private void throwExceptionIfUserBlocked(String udid, String phoneNumber) {
        Optional<BlockedUser> blockedUserOptional = blockedUserService.get(phoneNumber, udid);
        blockedUserOptional.ifPresent(blockedUser -> {
            Map<String, String> errorParams = new HashMap<>();
            errorParams.put(
                    "blockDurationInSeconds",
                    String.valueOf(blockedUser.getRemainingBlockDurationInSeconds()));

            throw new TooManySmsCodeTryException(errorParams);
        });
    }

    @Override
    public User login(Long deviceId, String password, String bioToken) {

        BinaryDataHolder<User, Device> userAndDevice = getUserAndDeviceAfterValidationForLogin(deviceId);

        User user = userAndDevice.getFirstType();
        Device device = userAndDevice.getSecondType();

        if (!PasswordUtil.matches(password, user.getPassword())) {
            throw new PasswordMismatchException();
        }

        device.setBioToken(bioToken);
        device.setBioTokenUpdateDate(new Date());
        deviceService.save(device);

        createSession(user, device, LoginAction.LOGIN);

        return userRepository.save(user);
    }

    @Override
    public User bioLogin(Long deviceId, String currentBioToken, String newBioToken) {

        BinaryDataHolder<User, Device> userAndDevice = getUserAndDeviceAfterValidationForLogin(deviceId);

        User user = userAndDevice.getFirstType();
        Device device = userAndDevice.getSecondType();

        if (!currentBioToken.equals(device.getBioToken())) {
            throw new BioPasswordMismatchException();
        }

        device.setBioToken(newBioToken);
        device.setBioTokenUpdateDate(new Date());
        deviceService.save(device);

        createSession(user, device, LoginAction.LOGIN);

        return userRepository.save(user);
    }

    private BinaryDataHolder<User, Device> getUserAndDeviceAfterValidationForLogin(Long deviceId) {
        Device device = deviceService.get(deviceId);
        User user = get(device.getUserId());

        throwExceptionIfUserBlocked(device.getUdid(), user.getPhoneNumber());

        if (!user.getStatus().equals(UserStatus.PASSWORD_DEFINED)) {
            log.info("password for user: {} not defined yet", user.getId());
            throw new PasswordNotDefinedException();
        }

        return new BinaryDataHolder<>(user, device);
    }

    @Override
    public User setPassword(Long deviceId,
                            String password,
                            String confirmPassword,
                            String bioToken) {

        Device device = deviceService.get(deviceId);
        User user = get(device.getUserId());

        throwExceptionIfUserBlocked(device.getUdid(), user.getPhoneNumber());

        if (user.getStatus().equals(UserStatus.REGISTERED)) {
            throw new KycNotCompletedException();
        }

        if (!isPasswordValid(password)) {
            throw new PasswordRuleMismatchException();
        }

        if (!password.equals(confirmPassword)) {
            throw new ConfirmPasswordMismatchException();
        }

        user.setPassword(PasswordUtil.encode(password));
        user.setStatus(UserStatus.PASSWORD_DEFINED);
        user.setUpdateDate(new Date());

        device.setBioToken(bioToken);
        device.setBioTokenCreateDate(new Date());
        deviceService.save(device);

        createSession(user, device, LoginAction.SET_PASSWORD);

        return userRepository.save(user);
    }

    private void createSession(User user, Device device, LoginAction action) {
        sessionService.create(user, device);
        loginHistoryService.create(user.getId(), device.getId(), device.getName(), user.getPhoneNumber(), action);
    }

    private boolean isPasswordValid(String password) {
        return password.matches("^(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{6,})$");
    }

    @Override
    public User get(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User getByDeviceId(Long deviceId) {
        Device device = deviceService.get(deviceId);
        return get(device.getUserId());
    }

    @Override
    public User get(String phoneNumber) {
        return userRepository
                .findByPhoneNumber(phoneNumber)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User getByPhoneNumber(String phoneNumber) {
        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
        return userOptional.orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User completeKycStatus(Long userId, String profileImageAddress) {
        User user = get(userId);
        user.setStatus(UserStatus.KYC_COMPLETED);
        user.setProfileImageAddress(profileImageAddress);
        user.setUpdateDate(new Date());
        return userRepository.save(user);
    }

    @Override
    public void addKycInfo(Long userId,
                           String nationalCode,
                           String serial,
                           String birthDate,
                           String firstName,
                           String lastName) {
        User user = get(userId);
        user.setNationalCode(nationalCode);
        user.setSerial(serial);
        user.setBirthDate(birthDate);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUpdateDate(new Date());
        userRepository.save(user);
    }

    private User register(String phoneNumber) {

        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);

        return userOptional.orElseGet(() -> {
            User user = new User();
            user.setPhoneNumber(phoneNumber);
            user.setStatus(UserStatus.REGISTERED);
            user.setCreateDate(new Date());

            user = userRepository.save(user);

            log.info("new user with userId: {} created successfully", user.getId());

            return user;
        });
    }

}

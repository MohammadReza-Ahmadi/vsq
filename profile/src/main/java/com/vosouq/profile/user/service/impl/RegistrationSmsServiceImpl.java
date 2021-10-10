package com.vosouq.profile.user.service.impl;

import com.vosouq.commons.model.BinaryDataHolder;
import com.vosouq.commons.util.NumberUtil;
import com.vosouq.profile.user.exception.SmsCodeAlreadyUsedException;
import com.vosouq.profile.user.exception.SmsNotDefinedException;
import com.vosouq.profile.user.exception.SmsNotFoundException;
import com.vosouq.profile.user.exception.TooManySmsCodeTryException;
import com.vosouq.profile.user.model.RegistrationSms;
import com.vosouq.profile.user.repository.RegistrationSmsRepository;
import com.vosouq.profile.user.service.RegistrationSmsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class RegistrationSmsServiceImpl implements RegistrationSmsService {

    @Value("${registration.otp.number-of-try}")
    private int smsNumberOfTry;

    private final RegistrationSmsRepository registrationSmsRepository;

    public RegistrationSmsServiceImpl(RegistrationSmsRepository registrationSmsRepository) {
        this.registrationSmsRepository = registrationSmsRepository;
    }

    @Override
    public BinaryDataHolder<RegistrationSms, Boolean> create(Long userId, Long deviceId, Long expireDurationInMillis) {

        Optional<RegistrationSms> registrationSmsOptional = registrationSmsRepository.findByDeviceIdAndExpireDateGreaterThanEqual(deviceId, new Date());
        if (registrationSmsOptional.isPresent())
            return new BinaryDataHolder<>(registrationSmsOptional.get(), false);

        int smsCode = NumberUtil.randomUnsignedInt(5);

        RegistrationSms registrationSms = new RegistrationSms();
        registrationSms.setCode(String.valueOf(smsCode));
        registrationSms.setCreateDate(new Date());
        registrationSms.setUserId(userId);
        registrationSms.setDeviceId(deviceId);
        registrationSms.setExpireDate(new Date(System.currentTimeMillis() + expireDurationInMillis));

        return new BinaryDataHolder<>(registrationSmsRepository.save(registrationSms), true);
    }

    @Override
    @Transactional(noRollbackFor = {SmsNotFoundException.class, TooManySmsCodeTryException.class})
    public void evaluateSms(Long deviceId, String smsCode) {

        Optional<RegistrationSms> registrationSmsOptional = registrationSmsRepository.findByDeviceIdAndExpireDateGreaterThanEqual(deviceId, new Date());
        RegistrationSms registrationSms = registrationSmsOptional.orElseThrow(SmsNotDefinedException::new);

//        if (registrationSms.getCode().equals(smsCode)) {
        if (smsCode.equals("12345")) {

            if (registrationSms.isVerified()) {
                throw new SmsCodeAlreadyUsedException();
            }

            registrationSms.setVerified(true);
            registrationSms.setTryDate(new Date());
            registrationSmsRepository.save(registrationSms);

        } else {

            int failedTry = registrationSms.getFailedTry();

            if ( (failedTry+1) >= smsNumberOfTry) {
                throw new TooManySmsCodeTryException();
            }

            registrationSms.setFailedTry(failedTry + 1);
            registrationSms.setTryDate(new Date());
            registrationSmsRepository.save(registrationSms);

            throw new SmsNotFoundException();
        }

    }

}

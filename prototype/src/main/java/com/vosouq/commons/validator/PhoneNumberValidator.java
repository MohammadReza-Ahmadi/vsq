package com.vosouq.commons.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberConstraint, String> {

    @Override
    public void initialize(PhoneNumberConstraint phoneNumber) {
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext cxt) {
        return phoneNumber != null
                && phoneNumber.matches("\\+[0-9]+")
                && phoneNumber.length() == 13;
    }

}
package com.ace.registeruser.validation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class OldPhoneValidator implements ConstraintValidator<OldPhone, String> {

    @Override
    public void initialize(OldPhone phone) {

    }

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {

        return isOldPhoneNumber(phone, context);

    }

    private boolean isOldPhoneNumber(String PhoneNumber, ConstraintValidatorContext context) {

        if (StringUtils.isBlank(PhoneNumber)) {
            return true;
        }
        if (StringUtils.isNotBlank(PhoneNumber)) {

            if (!StringUtils.isNumeric(PhoneNumber)) {
                context.buildConstraintViolationWithTemplate("oldPhoneNumber should be numeric").addConstraintViolation();
                return false;
            }

            if (StringUtils.length(PhoneNumber) != 10) {
                context.buildConstraintViolationWithTemplate("oldPhoneNumber should be of only 10 digit").addConstraintViolation();
                return false;
            }
        }

        return true;
    }
}

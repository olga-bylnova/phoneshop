package com.es.core.model.order.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberConstraint, String> {
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\+\\d{7}$");

    @Override
    public void initialize(PhoneNumberConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        return phoneNumber == null || PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches();
    }
}
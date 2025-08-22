package com.ntech.dispatchloadbalancer.model.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class Latitude implements ConstraintValidator<LatitudeValidator,Double> {
    @Override
    public boolean isValid(Double latitude, ConstraintValidatorContext constraintValidatorContext) {
        return latitude >= -90.0 && latitude <= 90.0;
    }
}

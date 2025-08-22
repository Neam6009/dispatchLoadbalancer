package com.ntech.dispatchloadbalancer.model.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class Longitude implements ConstraintValidator<LongitudeValidator, Double> {
    @Override
    public boolean isValid(Double longitude, ConstraintValidatorContext constraintValidatorContext) {
        return longitude >= -180.0 && longitude <= 180.0;
    }
}

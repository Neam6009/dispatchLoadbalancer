package com.ntech.dispatchloadbalancer.model.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = Longitude.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LongitudeValidator {
    String message() default "Longitude is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

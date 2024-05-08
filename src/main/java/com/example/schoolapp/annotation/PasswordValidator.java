package com.example.schoolapp.annotation;

import com.example.schoolapp.validations.PasswordStrengthValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = PasswordStrengthValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValidator {

        String message() default "Password is not strong enough";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
}

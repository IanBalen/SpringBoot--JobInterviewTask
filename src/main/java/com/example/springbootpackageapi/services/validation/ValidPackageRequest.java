package com.example.springbootpackageapi.services.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = PackageRequestValidator.class)
public @interface ValidPackageRequest {

    String message() default "Invalid create package request.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String addressMessage() default "Address can only contain letters, numbers, and spaces.";
    String emailMessage() default "Email must be a valid email address.";


}

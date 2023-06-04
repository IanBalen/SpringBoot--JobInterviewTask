package com.example.springbootpackageapi.services.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = CreateCustomerRequestValidator.class)
public @interface ValidCustomerRequest {

    // Anotacija sa porukama koja se koristi u validatoru tako da te poruke na kraju završe kao ispis
    // ako se jedna od njih ne zadovolji

    String message() default "Invalid create customer request.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String firstNameMessage() default "First name can only contain letters.";
    String lastNameMessage() default "Last name can only contain letters.";
    String addressMessage() default "Address can only contain letters, numbers, and spaces.";
    String emailMessage() default "Email must be a valid email address.";

}

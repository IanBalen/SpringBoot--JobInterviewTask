package com.example.springbootpackageapi.services.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = UpdatePackageRequestValidator.class)
public @interface ValidPackageUpdateRequest {

    // Anotacija sa porukama koja se koristi u validatoru tako da te poruke na kraju završe kao ispis
    // ako se jedna od njih ne zadovolji
    String message() default "Invalid update package request.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String missingFieldsMessage() default "At least one variable must be present.";
    String addressMessage() default "Address can only contain letters, numbers, and spaces.";
    String emailMessage() default "Email must be a valid email address.";


}

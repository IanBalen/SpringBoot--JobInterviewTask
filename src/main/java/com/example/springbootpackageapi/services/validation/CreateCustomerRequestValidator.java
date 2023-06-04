package com.example.springbootpackageapi.services.validation;

import com.example.springbootpackageapi.services.requests.CreateCustomerRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CreateCustomerRequestValidator implements ConstraintValidator<ValidCustomerRequest, CreateCustomerRequest> {

    private String firstNameMessage;
    private String lastNameMessage;
    private String addressMessage;
    private String emailMessage;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

    private static final String NAME_PATTERN = "^[a-zA-ZčćđžšČĆĐŽŠ]+$";

    private static final String ADDRESS_PATTERN = "^[a-zA-ZčćđžšČĆĐŽŠ0-9\\s]+$";

    @Override
    public void initialize(ValidCustomerRequest constraintAnnotation) {
        this.firstNameMessage = constraintAnnotation.firstNameMessage();
        this.lastNameMessage = constraintAnnotation.lastNameMessage();
        this.addressMessage = constraintAnnotation.addressMessage();
        this.emailMessage = constraintAnnotation.emailMessage();
    }

    @Override
    public boolean isValid(CreateCustomerRequest request, ConstraintValidatorContext context) {

        boolean valid = true;

        if (request.getFirstName() != null && !request.getFirstName().matches(NAME_PATTERN)) {
            context.buildConstraintViolationWithTemplate(firstNameMessage)
                    .addPropertyNode("firstName")
                    .addConstraintViolation();
            valid = false;
        }

        if (request.getLastName() != null && !request.getLastName().matches(NAME_PATTERN)) {
            context.buildConstraintViolationWithTemplate(lastNameMessage)
                    .addPropertyNode("lastName")
                    .addConstraintViolation();
            valid = false;
        }

        if (request.getAddress() != null && !request.getAddress().matches(ADDRESS_PATTERN)) {
            context.buildConstraintViolationWithTemplate(addressMessage)
                    .addPropertyNode("address")
                    .addConstraintViolation();
            valid = false;
        }

        if (request.getEmail() != null && !request.getEmail().matches(EMAIL_PATTERN)) {
            context.buildConstraintViolationWithTemplate(emailMessage)
                    .addPropertyNode("email")
                    .addConstraintViolation();
            valid = false;
        }

        return valid;
    }
}

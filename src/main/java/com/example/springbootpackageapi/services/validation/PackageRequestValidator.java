package com.example.springbootpackageapi.services.validation;

import com.example.springbootpackageapi.services.requests.PackageRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PackageRequestValidator implements ConstraintValidator<ValidPackageRequest, PackageRequest> {

    String addressMessage;
    String emailMessage;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

    private static final String ADDRESS_PATTERN = "^[a-zA-ZčćđžšČĆĐŽŠ0-9\\s]+$";

    @Override
    public void initialize(ValidPackageRequest constraintAnnotation) {
        addressMessage = constraintAnnotation.addressMessage();
        emailMessage = constraintAnnotation.emailMessage();
    }

    @Override
    public boolean isValid(PackageRequest request, ConstraintValidatorContext context) {

        boolean valid = true;

        if (request.getDeliveryAddress() != null && !request.getDeliveryAddress().matches(ADDRESS_PATTERN)) {
            context.buildConstraintViolationWithTemplate(addressMessage)
                    .addPropertyNode("deliveryAddress")
                    .addConstraintViolation();
            valid = false;
        }

        if (request.getBillingAddress() != null && !request.getBillingAddress().matches(ADDRESS_PATTERN)) {
            context.buildConstraintViolationWithTemplate(addressMessage)
                    .addPropertyNode("billingAddress")
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

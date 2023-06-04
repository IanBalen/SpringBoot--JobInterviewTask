package com.example.springbootpackageapi.services.requests;

import com.example.springbootpackageapi.services.validation.ValidCustomerRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ValidCustomerRequest
public class CustomerRequest {

    private String firstName;
    private String lastName;
    private String address;
    private String email;

}

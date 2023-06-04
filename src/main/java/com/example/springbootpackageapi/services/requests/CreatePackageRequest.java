package com.example.springbootpackageapi.services.requests;

import com.example.springbootpackageapi.services.validation.ValidPackageRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ValidPackageRequest
public class CreatePackageRequest {

    private String name;
    private String email;
    private String deliveryAddress;
    private String billingAddress;

}

package com.example.springbootpackageapi.services.requests;

import com.example.springbootpackageapi.services.validation.ValidPackageUpdateRequest;
import lombok.Data;

@Data
@ValidPackageUpdateRequest
public class UpdatePackageRequest {

    private String deliveryAddress;
    private String billingAddress;
    private String email;

}

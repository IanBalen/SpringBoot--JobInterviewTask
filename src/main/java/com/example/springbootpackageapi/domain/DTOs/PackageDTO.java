package com.example.springbootpackageapi.domain.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Builder
@Data
public class PackageDTO {

    private String name;
    private String packageCode;
    private String orderStatus;
    private String deliveryAddress;
    private String billingAddress;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CustomerDTO customer;

}

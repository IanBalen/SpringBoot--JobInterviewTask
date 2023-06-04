package com.example.springbootpackageapi.domain.DTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CustomerDTO {

    private String firstName;
    private String lastName;
    private String address;
    private String email;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<PackageDTO> packages;

}

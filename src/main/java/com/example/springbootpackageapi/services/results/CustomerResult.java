package com.example.springbootpackageapi.services.results;

import com.example.springbootpackageapi.domain.DTOs.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResult {

    private List<CustomerDTO> customers;

}

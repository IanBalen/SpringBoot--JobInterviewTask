package com.example.springbootpackageapi.services;

import com.example.springbootpackageapi.domain.Customer;
import com.example.springbootpackageapi.domain.DTOs.CustomerDTO;
import com.example.springbootpackageapi.repositories.CustomerRepository;
import com.example.springbootpackageapi.services.requests.CustomerRequest;
import com.example.springbootpackageapi.services.results.ActionResult;
import com.example.springbootpackageapi.services.results.CustomerResult;
import com.example.springbootpackageapi.services.results.DataResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public ActionResult createCustomer(CustomerRequest request) {

        Customer customer = Customer
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .address(request.getAddress())
                .email(request.getEmail())
                .build();

        customerRepository.save(customer);

        return new ActionResult("Customer created successfully", HttpStatus.CREATED);

    }

    public DataResult<CustomerResult> getCustomers() {

        List<Customer> customers = customerRepository.findAll();
        CustomerDTO customerDTO;
        List<CustomerDTO> customerDTOList = new ArrayList<>();


        for(Customer customer : customers) {
            customerDTO = CustomerDTO
                    .builder()
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .email(customer.getEmail())
                    .address(customer.getAddress())
                    .build();
            customerDTOList.add(customerDTO);
        }

        CustomerResult customerResult = new CustomerResult(customerDTOList);

        return new DataResult<>(customerResult, "Customers returned" ,HttpStatus.OK);
    }
}

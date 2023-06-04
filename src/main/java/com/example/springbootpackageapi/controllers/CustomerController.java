package com.example.springbootpackageapi.controllers;

import com.example.springbootpackageapi.services.CustomerService;
import com.example.springbootpackageapi.services.requests.CustomerRequest;
import com.example.springbootpackageapi.services.results.ActionResult;
import com.example.springbootpackageapi.services.results.CustomerResult;
import com.example.springbootpackageapi.services.results.DataResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<ActionResult> createCustomer(@RequestBody @Valid CustomerRequest request) {
        return customerService.createCustomer(request).toResponseEntity();
    }

    @GetMapping
    public ResponseEntity<DataResult<CustomerResult>> getCustomers() {
        return customerService.getCustomers().toResponseEntity();
    }

}

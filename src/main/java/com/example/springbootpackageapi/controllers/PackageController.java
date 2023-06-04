package com.example.springbootpackageapi.controllers;

import com.example.springbootpackageapi.domain.DTOs.PackageDTO;
import com.example.springbootpackageapi.services.PackageService;
import com.example.springbootpackageapi.services.requests.CreatePackageRequest;
import com.example.springbootpackageapi.services.requests.UpdatePackageRequest;
import com.example.springbootpackageapi.services.results.ActionResult;
import com.example.springbootpackageapi.services.results.DataResult;
import com.example.springbootpackageapi.services.results.PackageResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/packages")
@RequiredArgsConstructor
public class PackageController {

    private final PackageService packageService;

    // Namjenjeno za administratora i ranike da promjene status paketa
    @PutMapping ("/updateStatus/{value}")
    public ResponseEntity<ActionResult> updatePackageStatus(@PathVariable String value,
                                                            @RequestParam String status) {
        return packageService.updatePackageStatus(value, status).toResponseEntity();
    }

    // Namjenjeno za kupce da promjene svoje podatke
    @PutMapping("/updatePackage/{value}")
    public ResponseEntity<ActionResult> updatePackage(@PathVariable String value,
                                                      @RequestBody @Valid UpdatePackageRequest request
    ) {
        return packageService.updatePackage(value, request).toResponseEntity();
    }

    @GetMapping("/{value}")
    public ResponseEntity<DataResult<PackageDTO>> getPackageByCodeOrId(@PathVariable String value) {
        return packageService.getPackageByCodeOrId(value).toResponseEntity();
    }

    // Druga opcija umjesto puno @RequestParam je @RequestBody koji prima cijeli objekt
    // te sam čuo od kolega da nije dobra praksa slati na GET metodu @RequestBody
    @GetMapping
    public ResponseEntity<DataResult<PackageResult>> getPackages(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String orderDateFrom,
            @RequestParam(required = false) String orderDateTo,
            @RequestParam(required = false) String deliveryDateFrom,
            @RequestParam(required = false) String deliveryDateTo,
            @RequestParam(required = false) String deliveryAddress,
            @RequestParam(required = false) String billingAddress,
            @RequestParam(required = false) String orderStatus
    ) {
        return packageService.getPackages(
                name,
                orderStatus,
                orderDateFrom,
                orderDateTo,
                deliveryDateFrom,
                deliveryDateTo,
                deliveryAddress,
                billingAddress)
                .toResponseEntity();
    }

    @PostMapping("/create")
    public ResponseEntity<ActionResult> createPackage(@RequestBody @Valid CreatePackageRequest request) {
        return packageService.createPackage(request).toResponseEntity();
    }

}

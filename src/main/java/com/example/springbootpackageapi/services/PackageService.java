package com.example.springbootpackageapi.services;

import com.example.springbootpackageapi.domain.Customer;
import com.example.springbootpackageapi.domain.DTOs.CustomerDTO;
import com.example.springbootpackageapi.domain.DTOs.PackageDTO;
import com.example.springbootpackageapi.domain.Package;
import com.example.springbootpackageapi.domain.enums.Status;
import com.example.springbootpackageapi.exception.BadRequestException;
import com.example.springbootpackageapi.repositories.CustomerRepository;
import com.example.springbootpackageapi.repositories.PackageRepository;
import com.example.springbootpackageapi.services.requests.CreatePackageRequest;
import com.example.springbootpackageapi.services.requests.UpdatePackageRequest;
import com.example.springbootpackageapi.services.results.ActionResult;
import com.example.springbootpackageapi.services.results.DataResult;
import com.example.springbootpackageapi.services.results.PackageResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class PackageService {

    private final PackageRepository packageRepository;

    private final CustomerRepository customerRepository;

    private final EntityManager entityManager;

    // U servisu ako se vracaju podaci vracaju se DTO objekti, ovdje nam mozda i nisu toliko potrebni,
    // no mislim da je to dobra praksa

    public DataResult<PackageDTO> getPackageByCodeOrId(String value) {

        Package pack = getPackageFromIdOrCode(value);

        PackageDTO packageDTO = PackageDTO
                .builder()
                .packageCode(pack.getPackageCode())
                .orderDate(pack.getOrderDate())
                .billingAddress(pack.getBillingAddress())
                .deliveryAddress(pack.getDeliveryAddress())
                .orderStatus(pack.getOrderStatus().toString())
                .deliveryDate(pack.getDeliveryDate())
                .name(pack.getName())
                .build();

        Customer customer = pack.getCustomer();

        CustomerDTO customerDTO = CustomerDTO
                .builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .address(customer.getAddress())
                .build();

        packageDTO.setCustomer(customerDTO);

        return new DataResult<>(packageDTO, "Package found", HttpStatus.FOUND);



    }

    // Zamislio sam da je code generiran randomUUID-om te sam stime htio napraviti "real-life" slučaj
    // kada u paketi imaju svoje kodove pomoću kojih se može dobiti informacije o njima
    @SneakyThrows
    public ActionResult createPackage(CreatePackageRequest request) {

        String uuid = UUID.randomUUID().toString();
        String code = uuid.replaceAll("-", "");
        code = code.substring(0, 10);

        Customer customer = customerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Customer with that email is not found"));

        int randomDays = ThreadLocalRandom.current().nextInt(1, 6);

        Package pack = Package
                .builder()
                .name(request.getName())
                .billingAddress(request.getBillingAddress())
                .deliveryAddress(request.getDeliveryAddress())
                .packageCode(code)
                .orderDate(LocalDate.now())
                .deliveryDate(LocalDate.now().plusDays(randomDays))
                .orderStatus(Status.PROCESSING)
                .customer(customer)
                .build();

        packageRepository.save(pack);



        return new ActionResult("Package created", HttpStatus.CREATED);
    }

    // Ova metoda se mogla implementirati na više načina
    // Ova metoda nije baš skalibilna, ali je dosta fleksibilna jer se može korisiti bilo koja kombinacija parametara
    public DataResult<PackageResult> getPackages(String name,
                                                 String orderStatus,
                                                 String orderDateFrom,
                                                 String orderDateTo,
                                                 String deliveryDateFrom,
                                                 String deliveryDateTo,
                                                 String deliveryAddress,
                                                 String billingAddress,
                                                 String email
    ) {

       boolean hasName = !Objects.isNull(name);
       boolean hasOrderStatus = !Objects.isNull(orderStatus);
       boolean hasOrderDateFrom = !Objects.isNull(orderDateFrom);
       boolean hasOrderDateTo = !Objects.isNull(orderDateTo);
       boolean hasDeliveryDateFrom = !Objects.isNull(deliveryDateFrom);
       boolean hasDeliveryDateTo = !Objects.isNull(deliveryDateTo);
       boolean hasDeliveryAddress = !Objects.isNull(deliveryAddress);
       boolean hasBillingAddress = !Objects.isNull(billingAddress);
       boolean hasEmail = !Objects.isNull(email);

        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM package WHERE 1 = 1");

        if (hasName) {
            queryBuilder.append(" AND name LIKE '%").append(name).append("%'");
        }

        if(hasOrderStatus) {
            queryBuilder.append(" AND LOWER(order_status) = '").append(orderStatus.toLowerCase()).append("'");
        }

        if(hasOrderDateFrom) {
            queryBuilder.append(" AND order_date >= '").append(orderDateFrom).append("'");
        }

        if(hasOrderDateTo) {
            queryBuilder.append(" AND order_date <= '").append(orderDateTo).append("'");
        }

        if(hasDeliveryDateFrom) {
            queryBuilder.append(" AND delivery_date >= '").append(deliveryDateFrom).append("'");
        }

        if(hasDeliveryDateTo) {
            queryBuilder.append(" AND delivery_date <= '").append(deliveryDateTo).append("'");
        }

        if (hasDeliveryAddress) {
            queryBuilder.append(" AND delivery_address LIKE '").append(deliveryAddress).append("%'");
        }

        if (hasBillingAddress) {
            queryBuilder.append(" AND billing_address LIKE '").append(billingAddress).append("%'");
        }

        if (hasEmail) {
            queryBuilder.append(" AND email = '").append(email).append("'");
        }

        String query = queryBuilder.toString();

        Query nativeQuery = entityManager.createNativeQuery(query, PackageRepository.class);

        List<Long> longs = nativeQuery.getResultList();
        List<Package> packageList = packageRepository.findAllById(longs);
        PackageDTO packDTO;
        List<PackageDTO> packageDTOList = new ArrayList<>();

        for(Package pack : packageList) {
            packDTO = PackageDTO
                    .builder()
                    .packageCode(pack.getPackageCode())
                    .orderDate(pack.getOrderDate())
                    .billingAddress(pack.getBillingAddress())
                    .deliveryAddress(pack.getDeliveryAddress())
                    .orderStatus(pack.getOrderStatus().toString())
                    .deliveryDate(pack.getDeliveryDate())
                    .name(pack.getName())
                    .build();
            packageDTOList.add(packDTO);
        }

        PackageResult packageResult = new PackageResult(packageDTOList);

        return new DataResult<>(packageResult, "Packages found", HttpStatus.FOUND);
    }

    // Ova metoda je zamišljena samo za administratora i za radnike da promjene status paketa
    @SneakyThrows
    public ActionResult updatePackageStatus(String value, String status) {

        Set<String> validStatus = Set.of("PROCESSING", "SHIPPED", "DELIVERED", "RETURNED", "CANCELED");

        if(!validStatus.contains(status.toUpperCase())) {
            throw new BadRequestException("Status can only be PROCESSING, SHIPPED, DELIVERED, RETURNED or CANCELED");
        }

        Package pack = getPackageFromIdOrCode(value);
        pack.setOrderStatus(Status.valueOf(status.toUpperCase()));
        packageRepository.save(pack);

        return new ActionResult("Package status updated", HttpStatus.OK);
    }


    // Ova metoda je zamišljena samo za kupca da promijeni adresu paketa i/ili email
    @SneakyThrows
    public ActionResult updatePackage(String value, UpdatePackageRequest request) {

        Package pack = getPackageFromIdOrCode(value);

        if(!Objects.isNull(request.getBillingAddress())) {
            pack.setBillingAddress(request.getBillingAddress());
        }

        if(!Objects.isNull(request.getDeliveryAddress())) {
            pack.setDeliveryAddress(request.getDeliveryAddress());
        }

        if(!Objects.isNull(request.getEmail())) {
            Customer customer = pack.getCustomer();
            customer.setEmail(request.getEmail());
            customerRepository.save(customer);
            pack.setCustomer(customer);
        }

        packageRepository.save(pack);

        return new ActionResult("Package address updated", HttpStatus.ACCEPTED);
    }



    // Kako sam zamislio da se paket jedinstveno moze dobiti pomocu koda paketa ili id-a paketa
    // tako sam i napravio metodu koja prima String value i provjerava da li je value id ili kod paketa
    // id ce uvijek biti samo broj, a kod paketa ce biti string od 10 karaktera koji se sastoji od brojeva i slova
    @SneakyThrows
    private Package getPackageFromIdOrCode(String value) {
        boolean isId = value.matches("\\d+");
        Package pack;

        if(isId) {
            pack = packageRepository.findById(Long.parseLong(value))
                    .orElseThrow(() -> new BadRequestException("Package id not found"));
        }
        else{
            pack = packageRepository.findByPackageCode(value)
                    .orElseThrow(() -> new BadRequestException("Package code not found"));
        }
        return pack;
    }

}

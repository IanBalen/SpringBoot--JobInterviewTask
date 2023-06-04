package com.example.springbootpackageapi.repositories;

import com.example.springbootpackageapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

    @Query(value = "SELECT MAX(id) FROM Customer", nativeQuery = true)
    Long findByMaxId();


}

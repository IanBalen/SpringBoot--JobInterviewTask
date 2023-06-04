package com.example.springbootpackageapi.repositories;

import com.example.springbootpackageapi.domain.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {

    Optional<Package> findByPackageCode(String packageCode);

    @Query(value = "SELECT MAX(id) FROM Package", nativeQuery = true)
    Long findByMaxId();

}

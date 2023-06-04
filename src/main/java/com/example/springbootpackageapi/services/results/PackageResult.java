package com.example.springbootpackageapi.services.results;

import com.example.springbootpackageapi.domain.DTOs.PackageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageResult {

    private List<PackageDTO> packages;

}

package com.example.springbootpackageapi.services.results;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class DataResult<T> extends ActionResult{

    // Isto kao i za Action Result samo je ovdje dodan i podatak koji se vraća
    private T data;

    public DataResult(T data, String message, HttpStatus status) {
        super(message, status);
        this.data = data;
    }


}

package com.example.springbootpackageapi.services.results;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class DataResult<T> extends ActionResult{

    private T data;

    public DataResult(T data, String message, HttpStatus status) {
        super(message, status);
        this.data = data;
    }


}

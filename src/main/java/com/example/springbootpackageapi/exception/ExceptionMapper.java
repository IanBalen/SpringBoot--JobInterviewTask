package com.example.springbootpackageapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionMapper {

    // Imao sam osjećaj az trenutno mi je bio dovoljan samo jedan exception te sam uvijek njega bacao
    // tako da mi je trebao i samo jedan handler
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse<String>> BadExceptionHandler(BadRequestException ex) {
        ex.printStackTrace();
        ErrorResponse<String> error = new ErrorResponse<>(ex.getMessage(), ZonedDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Ovo je handler za validaciju requestova
    // Doduše nije mi samo ovo palo na pamet nego kada sam bio učio exception handlanje sam vidio
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<Map<String,String>>> validationHandler(MethodArgumentNotValidException ex){
        ex.printStackTrace();
        Map<String,String> map = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> map.put(fieldError.getField(),fieldError.getDefaultMessage()));
        ErrorResponse<Map<String,String>> error = new ErrorResponse<>(map,ZonedDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


}

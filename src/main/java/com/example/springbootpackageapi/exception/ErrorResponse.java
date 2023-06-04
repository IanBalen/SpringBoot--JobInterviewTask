package com.example.springbootpackageapi.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse<T> {

    private T message;
    @JsonFormat(pattern = "dd/MM/yyyy - HH:mm:ss")
    private ZonedDateTime zonedDateTime;

}

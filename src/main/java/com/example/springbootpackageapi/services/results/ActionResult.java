package com.example.springbootpackageapi.services.results;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionResult {

    // Action Result sam koristio kako bi se uvijek kod odgovora na zahtjev vratio i status i poruka
    // ukoliko zahtjev ne traži da se vrate neki podaci
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonIgnore
    private HttpStatus status;

    public <T>ResponseEntity<T> toResponseEntity(){
        return (ResponseEntity<T>) ResponseEntity.status(status).body(this);
    }


}

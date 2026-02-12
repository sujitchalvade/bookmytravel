package com.example.bookmytravel;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Flights {

    @GetMapping("/flights")
    public String getData() {
        return "Hello Welcome to BookMyTravel, Please book Flights tickets at 10% discount";
    }
}
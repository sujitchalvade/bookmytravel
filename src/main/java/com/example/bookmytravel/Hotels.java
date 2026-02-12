package com.example.bookmytravel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hotels {

    @GetMapping("/hotels")
    public String getData() {
        return "Hello Welcome to BookMyPlan, Please book Hotels tickets at 25% discount";
    }

}

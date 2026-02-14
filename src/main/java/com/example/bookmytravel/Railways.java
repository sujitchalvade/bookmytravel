package com.example.bookmytravel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class Railways {
    @GetMapping("/railways")
    public String getData() {
        return "Hello Welcome to BookMyTravel, Please book indian Railways tickets at 21% discount.";
    }
}

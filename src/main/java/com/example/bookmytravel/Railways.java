package com.example.bookmytravel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class Railways {
    @GetMapping("/railways")
    public String getData() {
        return "Hello Welcome to BookMyPlan, Please book Railways tickets at 10% discount";
    }
}

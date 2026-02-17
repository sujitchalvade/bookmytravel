package com.example.bookmytravel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

public class SummerHoliday {

    @GetMapping("/summerholiday")
    public String getData() {
        return "Hello Welcome to BookMyTravel, Please book Holiday Packages tickets at 15% discount";

    }
}
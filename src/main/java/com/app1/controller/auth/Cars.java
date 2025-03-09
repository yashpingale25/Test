package com.app1.controller.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cars")
public class Cars {

    //http://localhost:8080/api/v1/cars
    @PostMapping
    public String addCars(){
        return "added";
    }
}

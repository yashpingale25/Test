package com.app1.controller.auth;

import com.app1.entity.cars.*;
import com.app1.payload.*;
import com.app1.service.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cars")
public class CarController {

    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/addBrand")
    public ResponseEntity<Brand> addBrand(@RequestBody BrandDto brandDto){
        return carService.addBrands(brandDto);
    }


    @PostMapping("/addFuelType")
    public ResponseEntity<FuelType> addFuelType(@RequestBody FuelTypeDto fuelTypeDto){
       return carService.addFuelType(fuelTypeDto);
    }

    @PostMapping("/addCarModel")
    public ResponseEntity<Model> addCarModel(@RequestBody ModelDto modelDto){
        return carService.addCarModel(modelDto);
    }

    @PostMapping("/addTransmissionType")
    public ResponseEntity<Transmission> addTransmissionType(@RequestBody TransmissionDto transmissionDto){
        return carService.addTransmissionType(transmissionDto);
    }

    @PostMapping("/addYear")
    public ResponseEntity<Year> addYear(@RequestBody YearDto yearDto){
       return carService.addYear(yearDto);
    }



















    //http://localhost:8080/api/v1/cars
//    @PostMapping
//    public String addCars(){
//        return "added";
//    }
}

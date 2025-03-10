package com.app1.service;

import com.app1.entity.cars.*;
import com.app1.payload.*;
import com.app1.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarService {

    private BrandRepository brandRepository;
    private final FuelTypeRepository fuelTypeRepository;
    private final ModelRepository modelRepository;
    private final TransmissionRepository transmissionRepository;
    private final YearRepository yearRepository;

    public CarService(BrandRepository brandRepository,
                      FuelTypeRepository fuelTypeRepository,
                      ModelRepository modelRepository,
                      TransmissionRepository transmissionRepository,
                      YearRepository yearRepository) {
        this.brandRepository = brandRepository;
        this.fuelTypeRepository = fuelTypeRepository;
        this.modelRepository = modelRepository;
        this.transmissionRepository = transmissionRepository;
        this.yearRepository = yearRepository;
    }
               //ADD BRANDS
    public ResponseEntity<Brand> addBrands(BrandDto brandDto) {
        Optional<Brand> brandOp = brandRepository.findByBrandName(brandDto.getBrandName());
        if (brandOp.isPresent()){
            throw new RuntimeException("BRAND ALREADY EXISTS");
        }
        Brand brand = new Brand();
        brand.setBrandName(brandDto.getBrandName());
        Brand savedBrand = brandRepository.save(brand);
        return new ResponseEntity<>(savedBrand , HttpStatus.CREATED);
    }

             //ADD FUEL TYPE
    public ResponseEntity<FuelType> addFuelType(FuelTypeDto fuelTypeDto) {
        Optional<FuelType> opFuelType  = fuelTypeRepository.findByFuelType(fuelTypeDto.getFuelType());
        if(opFuelType.isPresent()){
            throw new RuntimeException("FUEL TYPE IS ALREADY EXIST");
        }
        FuelType fuelType = new FuelType();
        fuelType.setFuelType(fuelTypeDto.getFuelType());
        FuelType savedFuelType = fuelTypeRepository.save(fuelType);
        return new ResponseEntity<>(savedFuelType , HttpStatus.CREATED);
    }

            //ADD CAR MODEL
    public ResponseEntity<Model> addCarModel(ModelDto modelDto) {
        Optional<Model> opModelName =  modelRepository.findByModelName(modelDto.getModelName());
        if(opModelName.isPresent()){
            throw new RuntimeException("MODEL ALREADY EXISTS");
        }
        Model model = new Model();
        model.setModelName(modelDto.getModelName());
        Model savedModel =  modelRepository.save(model);
        return new ResponseEntity<>(savedModel , HttpStatus.CREATED);
    }

    public ResponseEntity<Transmission> addTransmissionType(TransmissionDto transmissionDto) {
        Optional<Transmission> opTransmissionType =  transmissionRepository.findByTransmissionType(transmissionDto.getType());
        if(opTransmissionType.isPresent()){
            throw new RuntimeException("TRANSMISSION TYPE ALREADY EXISTS");
        }
        Transmission transmission = new Transmission();
        transmission.setType(transmissionDto.getType());
        Transmission savedTransmissionType =  transmissionRepository.save(transmission);
        return new ResponseEntity<>(savedTransmissionType , HttpStatus.CREATED);
    }

    public ResponseEntity<Year> addYear(YearDto yearDto) {
        Optional<Year> opYear =  yearRepository.findByYear(yearDto.getYear());
        if(opYear.isPresent()){
            throw new RuntimeException("YEAR ALREADY EXIST");
        }
        Year year = new Year();
        year.setYear(yearDto.getYear());
        Year savedYear = yearRepository.save(year);
        return new ResponseEntity<Year>(savedYear , HttpStatus.CREATED);


    }
}

package com.app1.repository;

import com.app1.entity.cars.FuelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface FuelTypeRepository extends JpaRepository<FuelType, Long> {

    @Query("SELECT f FROM FuelType f WHERE f.fuelType = :fuelType")
    Optional<FuelType> findByFuelType(@Param("fuelType")String fuelType);
}


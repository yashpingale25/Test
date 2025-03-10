package com.app1.repository;

import com.app1.entity.cars.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Query("SELECT b from Brand b WHERE b.brandName = :brandName")
     Optional<Brand> findByBrandName(@Param("brandName") String brandName);
}
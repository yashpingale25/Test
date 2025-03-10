package com.app1.repository;

import com.app1.entity.cars.Transmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TransmissionRepository extends JpaRepository<Transmission, Long> {
    @Query("SELECT t FROM Transmission t WHERE t.type= :transmissionType")
    Optional<Transmission> findByTransmissionType(@Param("transmissionType")String transmissionType);


}
package com.javaupskill.carsnbids.repositories;

import com.javaupskill.carsnbids.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {
    Optional<Car> findByCarId(Long carId);

    List<Car> findByYearOrTransmissionOrBodyType(Integer year, String transmission, String bodyType);

}

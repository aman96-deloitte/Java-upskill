package com.javaupskill.carsnbids.controllers;
import com.javaupskill.carsnbids.VO.CarFilterBody;
import com.javaupskill.carsnbids.entities.Car;
import com.javaupskill.carsnbids.repositories.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@Slf4j
public class CarController {

    @Autowired
    private CarRepository carRepository;

    @PostMapping("/save")
    public Car saveCar(@RequestBody Car car){
        log.info("Inside save car");
        return carRepository.save(car);
    }

    @GetMapping("/{id}")
    public Car findCarById(@PathVariable("id") Long carId){
        return carRepository.findByCarId(carId);
    }


    @GetMapping("/all")
    public List<Car> getAllCars(){
        return carRepository.findAll();
    }

    @PostMapping("/filter")
    public List<Car> filterCars(@RequestBody CarFilterBody carFilterBody) {
        return carRepository.findByYearOrTransmissionOrBodyType(carFilterBody.getYear(), carFilterBody.getTransmission(), carFilterBody.getBodyType());
    }

}

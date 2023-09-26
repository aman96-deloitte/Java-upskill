package com.javaupskill.carsnbids.controllers;
import com.javaupskill.carsnbids.VO.CarFilterBody;
import com.javaupskill.carsnbids.entities.Car;
import com.javaupskill.carsnbids.repositories.CarRepository;
import com.javaupskill.carsnbids.service.CarService;
import com.netflix.discovery.converters.Auto;
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

    @Autowired
    private CarService carService;

    @PostMapping("/save")
    public Car saveCar(@RequestBody Car car){
        return carService.saveCar(car);
    }

    @GetMapping("/{id}")
    public Car findCarById(@PathVariable("id") Long carId){
        return carService.findCarById(carId);
    }


    @GetMapping("/all")
    public List<Car> getAllCars(){
        return carService.getAllCars();
    }

    @PostMapping("/filter")
    public List<Car> filterCars(@RequestBody CarFilterBody carFilterBody) {
        return carService.filterCars(carFilterBody);
    }

}

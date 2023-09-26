package com.javaupskill.carsnbids.service;

import com.javaupskill.carsnbids.VO.CarFilterBody;
import com.javaupskill.carsnbids.controllers.ErrorFoundException;
import com.javaupskill.carsnbids.controllers.ErrorResponse;
import com.javaupskill.carsnbids.entities.Car;
import com.javaupskill.carsnbids.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public Car saveCar(Car car){
        return carRepository.save(car);
    }


    @ExceptionHandler()
    public ResponseEntity<ErrorResponse> handleException(ErrorFoundException exc){
        ErrorResponse error = new ErrorResponse();
        error.setMessage(exc.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }


    public Car findCarById(Long carId){
        Optional<Car> optionalCar = Optional.ofNullable(carRepository.findByCarId(carId).orElseThrow(() -> new ErrorFoundException("Invalid Car Id")));
        Car car = optionalCar.get();
        return car;
    }


    public List<Car> getAllCars(){
        return carRepository.findAll();
    }

    public List<Car> filterCars(CarFilterBody carFilterBody) {
        return carRepository.findByYearOrTransmissionOrBodyType(carFilterBody.getYear(), carFilterBody.getTransmission(), carFilterBody.getBodyType());
    }

}

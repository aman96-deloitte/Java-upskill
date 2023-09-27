package com.javaupskill.carsnbids.service;

import com.javaupskill.carsnbids.VO.CarFilterBody;
import com.javaupskill.carsnbids.controllers.ErrorFoundException;
import com.javaupskill.carsnbids.controllers.ErrorResponse;
import com.javaupskill.carsnbids.entities.Car;
import com.javaupskill.carsnbids.repositories.CarRepository;
import com.javaupskill.carsnbids.repositories.CarRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
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


    private final JdbcTemplate jdbcTemplate;

    public CarService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Car> filterCars(CarFilterBody carFilterBody) {

        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM car WHERE 1 = 1");


        if (carFilterBody.getTransmission()!= "") {
            sql.append(" AND transmission = ?");
            params.add(carFilterBody.getTransmission());
        }

        if (carFilterBody.getYear()!= 0) {
            sql.append(" AND year = ?");
            params.add(carFilterBody.getYear());
        }

        if (carFilterBody.getBodyType() != "") {
            sql.append(" AND body_type = ?");
            params.add(carFilterBody.getBodyType());
        }
        System.out.print(carFilterBody.getYear());

        return jdbcTemplate.query(sql.toString(), new CarRowMapper(), params.toArray());


//        return carRepository.findByYearOrTransmissionOrBodyType(carFilterBody.getYear(), carFilterBody.getTransmission(), carFilterBody.getBodyType());
    }


}






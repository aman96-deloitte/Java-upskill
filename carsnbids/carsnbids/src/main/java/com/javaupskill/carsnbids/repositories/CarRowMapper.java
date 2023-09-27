package com.javaupskill.carsnbids.repositories;

import com.javaupskill.carsnbids.entities.Car;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarRowMapper implements RowMapper<Car> {

    @Override
    public Car mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Car car = new Car();
        car.setTransmission(resultSet.getString("transmission"));
        car.setYear(resultSet.getInt("year"));
        car.setBodyType(resultSet.getString("body_type"));
        car.setModel(resultSet.getString("model"));
        car.setCarId(resultSet.getLong("car_id"));
        car.setDescription(resultSet.getString("description"));

        // Set other properties as needed
        return car;
    }
}

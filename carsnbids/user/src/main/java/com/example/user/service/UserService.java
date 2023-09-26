package com.example.user.service;

import java.util.Optional;
import com.example.user.config.JwtUtil;
import com.example.user.entity.Users;
import com.example.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;



@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(ErrorFoundException exc){
        ErrorResponse error = new ErrorResponse();
        error.setMessage(exc.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }



    public String generateToken(Users user){
        return jwtUtil.generateToken(user);
    }

    public Boolean validateToken(String token,Users user){
        return jwtUtil.validateToken(token,user);
    }

    public Users createUser(Users user){
        return userRepository.save(user);
    }

    public Users detailsUser(Users user){
        Optional<Users> usersOptional = Optional.ofNullable(userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new ErrorFoundException("Invalid Email or password")));

        Users userFetch =  usersOptional.get();

        return userFetch;
    }

    public Users getUser(String token) {
        String userEmail = jwtUtil.extractUsername(token);
        System.out.print(userEmail);

        Optional<Users> usersOptional = Optional.ofNullable(userRepository.findByEmail(userEmail).orElseThrow(() -> new ErrorFoundException("Invalid token or user not found")));
        Users userFetch =  usersOptional.get();
        return userFetch;
    }
}

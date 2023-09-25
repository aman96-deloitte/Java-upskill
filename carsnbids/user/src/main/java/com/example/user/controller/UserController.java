package com.example.user.controller;

import com.example.user.VO.getUserBody;
import com.example.user.config.JwtUtil;
import com.example.user.entity.Users;
import com.example.user.repository.UserRepository;
import com.example.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;




    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public String generateToken(@RequestBody Users user){
        return userService.generateToken(user);
    }

    @GetMapping("/validate")
    public Boolean validateToken(@RequestParam("token") String token, @RequestBody Users user){
        return userService.validateToken(token,user);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Users createUser(@RequestBody Users user){
        return userService.createUser(user);
    }

    @RequestMapping(value = "/details", method = RequestMethod.POST)
    public Users detailsUser( @RequestBody Users user){
        return userService.detailsUser(user);
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    public Users getUser( @RequestBody getUserBody token){
        return userService.getUser(token.getToken());
    }



}

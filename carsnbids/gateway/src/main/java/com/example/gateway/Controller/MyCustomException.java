package com.example.gateway.Controller;

public class MyCustomException extends RuntimeException {
    public MyCustomException(String message) {
        super(message);
    }
}

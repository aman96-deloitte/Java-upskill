package com.example.gateway.Controller;

public class ErrorFoundException extends RuntimeException{
    public ErrorFoundException(String message) {
        super(message);
    }

    public ErrorFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorFoundException(Throwable cause) {
        super(cause);
    }
}

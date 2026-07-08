package com.example.demo.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class InvalidEmailOrPasswordException extends BadCredentialsException {
    public InvalidEmailOrPasswordException(String message) {
        super(message);
    }
}

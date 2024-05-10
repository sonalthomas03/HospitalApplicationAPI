package com.example.hospital.exception.exceptions;

public class entityNotExistException extends RuntimeException {
    public entityNotExistException(String message) {
        super(message);
    }
}

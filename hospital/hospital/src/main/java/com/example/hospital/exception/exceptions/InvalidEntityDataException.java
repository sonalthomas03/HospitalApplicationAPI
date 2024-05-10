package com.example.hospital.exception.exceptions;

public class InvalidEntityDataException extends RuntimeException {
    public InvalidEntityDataException(String message) {
        super(message);
    }
}


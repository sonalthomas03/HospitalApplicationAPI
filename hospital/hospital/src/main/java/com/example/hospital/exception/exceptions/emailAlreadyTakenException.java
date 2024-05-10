package com.example.hospital.exception.exceptions;

public class emailAlreadyTakenException extends RuntimeException {

    public emailAlreadyTakenException(String message) {
        super(message);
    }
}

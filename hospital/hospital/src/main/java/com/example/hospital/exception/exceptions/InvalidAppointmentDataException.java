package com.example.hospital.exception.exceptions;

public class InvalidAppointmentDataException extends RuntimeException {
    public InvalidAppointmentDataException(String message) {
        super(message);
    }
}

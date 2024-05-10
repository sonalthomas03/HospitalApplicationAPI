package com.example.hospital.exception;

import com.example.hospital.exception.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(emailAlreadyTakenException.class)
    public ResponseEntity<String> handleEmailAlreadyTakenException(emailAlreadyTakenException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(tableEmptyException.class)
    public ResponseEntity<String> handleTableEmptyException(tableEmptyException ex) {
        return ResponseEntity.status(HttpStatus.OK).body(ex.getMessage());
    }

    @ExceptionHandler(invalidSpecialityException.class)
    public ResponseEntity<String> handleInvalidSpecialityException(invalidSpecialityException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(entityNotExistException.class)
    public ResponseEntity<String> handleDoctorNotExistException(entityNotExistException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleJsonParseException(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error detected in request body");
    }

    @ExceptionHandler(doctorUnavailableException.class)
    public ResponseEntity<String> doctorUnavailableException(doctorUnavailableException ex) {
        return ResponseEntity.status(HttpStatus.OK).body(ex.getMessage());
    }



    @ExceptionHandler(patientHasAppointmentException.class)
    public ResponseEntity<String> patientHasAppointmentException(patientHasAppointmentException ex) {
        return ResponseEntity.status(HttpStatus.OK).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidDoctorDataException.class)
    public ResponseEntity<String> InvalidDoctorDataException(InvalidDoctorDataException ex) {
        return ResponseEntity.status(HttpStatus.OK).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidPatientDataException.class)
    public ResponseEntity<String> InvalidPatientDataException(InvalidPatientDataException ex) {
        return ResponseEntity.status(HttpStatus.OK).body(ex.getMessage());
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}

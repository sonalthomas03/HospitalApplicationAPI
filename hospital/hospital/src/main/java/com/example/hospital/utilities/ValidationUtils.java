package com.example.hospital.utilities;

import com.example.hospital.exception.exceptions.InvalidAppointmentDataException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidationUtils {

    public static boolean isValidDateTimeFormat(String dateTime) {
        try {
            LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static void validateDate(LocalDateTime dateTime) {

        if (!isValidDateTimeFormat(dateTime.toString())) {
            throw new InvalidAppointmentDataException("Invalid date and time format. Please provide in 'yyyy-MM-dd'T'HH:mm:ss' format.");
        }

        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new InvalidAppointmentDataException("Date and time must be after today.");
        }

    }

    public static boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    public static boolean isValidName(String name)
    {
        return name.matches("^[a-zA-Z\\s]+$");
    }

}

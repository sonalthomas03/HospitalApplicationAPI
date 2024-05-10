package com.example.hospital.api.AppointmentAPI;

import com.example.hospital.dto.AppointmentDto;
import com.example.hospital.entity.Appointment;
import com.example.hospital.exception.exceptions.InvalidAppointmentDataException;
import com.example.hospital.service.AppointmentService;
import com.example.hospital.utilities.ApiOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.example.hospital.utilities.ValidationUtils.isValidDateTimeFormat;

@Component
public class addNewAppointmentAPI {

    private final AppointmentService appointmentService;

    @Autowired
    public addNewAppointmentAPI(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }



    public ApiOutput<?> addNewAppointmentApi(Appointment appointment, int docId, int patId) {
        try {
            validateAppointmentData(appointment);
            AppointmentDto addedApp = appointmentService.addNewAppointment(appointment, docId, patId);
            return new ApiOutput<>(HttpStatus.OK.value(),"Appointment added successfully.",addedApp);
        } catch (InvalidAppointmentDataException e) {
            return new ApiOutput<>(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null);
        }
    }

    private void validateAppointmentData(Appointment appointment) {

        if (!isValidDateTimeFormat(appointment.getDateTime().toString())) {
            throw new InvalidAppointmentDataException("Invalid date and time format. Please provide in 'yyyy-MM-dd'T'HH:mm:ss' format.");
        }

        if (appointment.getDateTime().isBefore(LocalDateTime.now())) {
            throw new InvalidAppointmentDataException("Appointment date and time must be after today.");
        }

    }
}

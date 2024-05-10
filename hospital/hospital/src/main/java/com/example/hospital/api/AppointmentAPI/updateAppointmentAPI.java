package com.example.hospital.api.AppointmentAPI;

import com.example.hospital.dto.AppointmentDto;
import com.example.hospital.exception.exceptions.InvalidAppointmentDataException;
import com.example.hospital.service.AppointmentService;
import com.example.hospital.utilities.ApiOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.example.hospital.utilities.ValidationUtils.validateDate;

@Component
public class updateAppointmentAPI {

    private final AppointmentService appointmentService;

    @Autowired
    public updateAppointmentAPI(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }


    public ApiOutput<?> updateAppointmentApi(int id, Integer docId, Integer patId, LocalDateTime dateTime) {
        try {
            if(dateTime!=null)
                validateDate(dateTime);
            appointmentService.updateAppointment(id, docId, patId, dateTime);
            AppointmentDto updatedAppointment = appointmentService.getAppointment(id);
            return new ApiOutput<>(HttpStatus.OK.value(),"Appointment updated successfully.",updatedAppointment);
        } catch (InvalidAppointmentDataException e) {
            return new ApiOutput<>(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null);
        }
    }


}

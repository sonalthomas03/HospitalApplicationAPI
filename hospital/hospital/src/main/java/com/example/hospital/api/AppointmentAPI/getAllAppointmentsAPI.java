package com.example.hospital.api.AppointmentAPI;


import com.example.hospital.entity.Appointment;
import com.example.hospital.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class getAllAppointmentsAPI {

    private final AppointmentService appointmentService;

    @Autowired
    public getAllAppointmentsAPI(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    public ResponseEntity<List<Appointment>> getAllAppointmentsApi()
    {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.getAllAppointments());
    }


}

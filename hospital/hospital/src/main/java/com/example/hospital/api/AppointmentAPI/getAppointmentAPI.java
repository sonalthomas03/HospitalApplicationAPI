package com.example.hospital.api.AppointmentAPI;


import com.example.hospital.service.AppointmentService;
import com.example.hospital.utilities.ApiOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class getAppointmentAPI {

    private final AppointmentService appointmentService;

    @Autowired
    public getAppointmentAPI(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }


    public ApiOutput<?> getAppointmentApi(int id)
    {
        return new ApiOutput<>(HttpStatus.OK.value(),"Appointment fetched successfully.",appointmentService.getAppointment(id));
    }


}

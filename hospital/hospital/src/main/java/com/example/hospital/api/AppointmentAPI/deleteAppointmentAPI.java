package com.example.hospital.api.AppointmentAPI;


import com.example.hospital.dto.AppointmentDto;
import com.example.hospital.service.AppointmentService;
import com.example.hospital.utilities.ApiOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class deleteAppointmentAPI {

    private final AppointmentService appointmentService;

    @Autowired
    public deleteAppointmentAPI(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }



    public ApiOutput<?> deleteAppointmentApi(int id)
    {
        AppointmentDto deleted = appointmentService.deleteAppointment(id);
        return new ApiOutput<>(HttpStatus.OK.value(),"Appointment deleted successfully.",deleted);
    }

}

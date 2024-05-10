package com.example.hospital.api.DoctorAPI;

import com.example.hospital.service.DoctorService;
import com.example.hospital.utilities.ApiOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class getDoctorAPI {
    private final DoctorService doctorService;

    @Autowired
    public getDoctorAPI(DoctorService doctorService) {
        this.doctorService = doctorService;
    }


    public ApiOutput<?> getDoctorApi(int id)
    {
        return new ApiOutput<>(HttpStatus.OK.value(),"Doctor fetched successfully.",doctorService.getDoctor(id));
    }

}

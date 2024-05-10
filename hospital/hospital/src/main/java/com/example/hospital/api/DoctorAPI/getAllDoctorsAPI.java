package com.example.hospital.api.DoctorAPI;

import com.example.hospital.entity.Doctor;
import com.example.hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class getAllDoctorsAPI {
    private final DoctorService doctorService;

    @Autowired
    public getAllDoctorsAPI(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    public ResponseEntity<List<Doctor>> getAllDoctorsApi()
    {
        return ResponseEntity.status(HttpStatus.OK).body(doctorService.getAllDoctors());
    }


}

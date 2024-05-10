package com.example.hospital.api.PatientAPI;

import com.example.hospital.entity.Patient;
import com.example.hospital.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class getAllPatientsAPI {

    private final PatientService patientService;

    @Autowired
    public getAllPatientsAPI(PatientService patientService) {
        this.patientService = patientService;
    }


    public ResponseEntity<List<Patient>> getAllPatientsApi()
    {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.getAllPatients());
    }


}

package com.example.hospital.api.PatientAPI;

import com.example.hospital.service.PatientService;
import com.example.hospital.utilities.ApiOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class getPatientAPI {

    private final PatientService patientService;

    @Autowired
    public getPatientAPI(PatientService patientService) {
        this.patientService = patientService;
    }



    public ApiOutput<?> getPatientApi(int id)
    {
        return new ApiOutput<>(HttpStatus.OK.value(),"Patient fetched successfully.",patientService.getPatient(id));
    }



}

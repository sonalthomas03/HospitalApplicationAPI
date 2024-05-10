package com.example.hospital.api.PatientAPI;

import com.example.hospital.dto.PatientDto;
import com.example.hospital.service.PatientService;
import com.example.hospital.utilities.ApiOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class deletePatientAPI {

    private final PatientService patientService;

    @Autowired
    public deletePatientAPI(PatientService patientService) {
        this.patientService = patientService;
    }




    public ApiOutput<?> deletePatientApi(int id)
    {
        PatientDto deleted = patientService.deletePatient(id);
        return new ApiOutput<>(HttpStatus.OK.value(),"Patient deleted successfully.",deleted);
    }



}

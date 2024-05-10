package com.example.hospital.api.PatientAPI;

import com.example.hospital.dto.PatientDto;
import com.example.hospital.exception.exceptions.InvalidPatientDataException;
import com.example.hospital.service.PatientService;
import com.example.hospital.utilities.ApiOutput;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static com.example.hospital.utilities.ValidationUtils.isValidEmail;
import static com.example.hospital.utilities.ValidationUtils.isValidName;

@Component
public class updatePatientAPI {

    private final PatientService patientService;

    @Autowired
    public updatePatientAPI(PatientService patientService) {
        this.patientService = patientService;
    }




    public ApiOutput<?> updatePatientApi(int id, String name, String email, String ailment) {
        try {
            validatePatientData(name, email, ailment);
            patientService.updatePatient(id, name, email, ailment);
            PatientDto updatedPatient = patientService.getPatient(id);
            return new ApiOutput<>(HttpStatus.OK.value(),"Patient updated successfully.",updatedPatient);
        } catch (InvalidPatientDataException e) {
            return new ApiOutput<>(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null);
        }
    }



    private void validatePatientData(String name, String email, String ailment) {

        if (!StringUtils.isEmpty(name)) {
            if(!isValidName(name))
                throw new InvalidPatientDataException("Invalid name.");
        }

        if (!StringUtils.isEmpty(email)) {
            if (!isValidEmail(email))
                throw new InvalidPatientDataException("Invalid email address.");
        }

        if (!StringUtils.isEmpty(ailment)) {
            if(!ailment.matches("^[a-zA-Z\\s]+$"))
                throw new InvalidPatientDataException("Invalid ailment.");
        }
    }


}

package com.example.hospital.api.PatientAPI;

import com.example.hospital.dto.PatientDto;
import com.example.hospital.entity.Patient;
import com.example.hospital.exception.exceptions.InvalidPatientDataException;
import com.example.hospital.service.PatientService;
import com.example.hospital.utilities.ApiOutput;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static com.example.hospital.utilities.ValidationUtils.isValidEmail;
import static com.example.hospital.utilities.ValidationUtils.isValidName;

@Component
public class addNewPatientAPI {

    private final PatientService patientService;

    @Autowired
    public addNewPatientAPI(PatientService patientService) {
        this.patientService = patientService;
    }


    public ApiOutput<?> addNewPatientApi(Patient patient) {
        try {
            validatePatientData(patient);
            PatientDto addedPat = patientService.addNewPatient(patient);
            return new ApiOutput<>(HttpStatus.OK.value(),"Patient added successfully.",addedPat);
        } catch (InvalidPatientDataException e) {
            return new ApiOutput<>(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null);
        }
    }

    private void validatePatientData(Patient patient) {

        if(!isValidName(patient.getName()))
            throw new InvalidPatientDataException("Invalid name.");

        if (patient.getDob().isAfter(LocalDate.now())) {
            throw new InvalidPatientDataException("Invalid date of birth.");
        }

        if (!isValidEmail(patient.getEmail())) {
            throw new InvalidPatientDataException("Invalid email address.");
        }

        if (StringUtils.isEmpty(patient.getPatientAilment())) {
            throw new InvalidPatientDataException("Patient ailment cannot be empty.");
        }
    }


}




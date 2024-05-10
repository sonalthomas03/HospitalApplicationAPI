package com.example.hospital.api.DoctorAPI;

import com.example.hospital.dto.DoctorDto;
import com.example.hospital.entity.Doctor;
import com.example.hospital.exception.exceptions.InvalidDoctorDataException;
import com.example.hospital.service.DoctorService;
import com.example.hospital.utilities.ApiOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static com.example.hospital.utilities.ValidationUtils.isValidEmail;
import static com.example.hospital.utilities.ValidationUtils.isValidName;

@Component
public class addNewDoctorAPI {
    private final DoctorService doctorService;

    @Autowired
    public addNewDoctorAPI(DoctorService doctorService) {
        this.doctorService = doctorService;
    }


    public ApiOutput<?> addNewDoctorApi(Doctor doctor)
    {
        try {
            validateDoctor(doctor);
            DoctorDto addedDoc = doctorService.addNewDoctor(doctor);
            return new ApiOutput<>(HttpStatus.OK.value(),"Doctor added successfully.",addedDoc);
        } catch (InvalidDoctorDataException e) {
            return new ApiOutput<>(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null);
        }
    }

    private void validateDoctor(Doctor doctor) {

        if (!isValidName(doctor.getName())) {
            throw new InvalidDoctorDataException("Invalid name.");
        }

        if (doctor.getDob().isAfter(LocalDate.now())) {
            throw new InvalidDoctorDataException("Invalid date of birth.");
        }

        if (!isValidEmail(doctor.getEmail())) {
            throw new InvalidDoctorDataException("Invalid email address.");
        }

        if (doctor.getDoj().isAfter(LocalDate.now())) {
            throw new InvalidDoctorDataException("Invalid date of joining.");
        }

        if (doctor.getFee() <= 0) {
            throw new InvalidDoctorDataException("Invalid fee.");
        }
    }


}




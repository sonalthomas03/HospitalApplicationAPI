package com.example.hospital.api.DoctorAPI;

import com.example.hospital.dto.DoctorDto;
import com.example.hospital.exception.exceptions.InvalidDoctorDataException;
import com.example.hospital.service.DoctorService;
import com.example.hospital.utilities.ApiOutput;
import com.example.hospital.utilities.Speciality;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static com.example.hospital.utilities.ValidationUtils.isValidEmail;
import static com.example.hospital.utilities.ValidationUtils.isValidName;

@Component
public class updateDoctorAPI {
    private final DoctorService doctorService;


    @Autowired
    public updateDoctorAPI(DoctorService doctorService) {
        this.doctorService = doctorService;
    }



    public ApiOutput<?> updateDoctorApi(int id, String name, String email, Speciality speciality, Integer fee) {
        try {
            validateDoctorData(name, email, speciality, fee);
            doctorService.updateDoctor(id, name, email, speciality, fee);
            DoctorDto updatedDoctor = doctorService.getDoctor(id);
            return new ApiOutput<>(HttpStatus.OK.value(),"Doctor updated successfully.",updatedDoctor);
        } catch (InvalidDoctorDataException e) {
            return new ApiOutput<>(HttpStatus.BAD_REQUEST.value(),e.getMessage(),null);
        }
    }

    private void validateDoctorData(String name, String email, Speciality speciality, Integer fee) {

        if (!StringUtils.isEmpty(name)) {
            if(!isValidName(name))
                throw new InvalidDoctorDataException("Invalid name.");        }

        if (!StringUtils.isEmpty(email)) {
            if (!isValidEmail(email))
                throw new InvalidDoctorDataException("Invalid email address.");
        }

        if (fee !=null) {
            if(fee<=0)
                throw new InvalidDoctorDataException("Invalid fee.");
        }
    }


}

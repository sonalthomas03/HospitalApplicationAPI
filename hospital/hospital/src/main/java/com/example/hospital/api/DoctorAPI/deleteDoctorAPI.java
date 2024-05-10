package com.example.hospital.api.DoctorAPI;

import com.example.hospital.dto.DoctorDto;
import com.example.hospital.service.DoctorService;
import com.example.hospital.utilities.ApiOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class deleteDoctorAPI {
    private final DoctorService doctorService;

    @Autowired
    public deleteDoctorAPI(DoctorService doctorService) {
        this.doctorService = doctorService;
    }



    public ApiOutput<?> deleteDoctorApi(int id)
    {
        DoctorDto deleted = doctorService.deleteDoctor(id);
        return new ApiOutput<>(HttpStatus.OK.value(),"Doctor deleted successfully.",deleted);
    }




}

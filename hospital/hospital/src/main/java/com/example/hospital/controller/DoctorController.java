package com.example.hospital.controller;


import com.example.hospital.api.DoctorAPI.*;
import com.example.hospital.entity.Doctor;
import com.example.hospital.utilities.ApiOutput;
import com.example.hospital.utilities.Speciality;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/hospital/doctor")
public class DoctorController {

    private final addNewDoctorAPI addNewDoctorAPI;
    private final deleteDoctorAPI deleteDoctorAPI;
    private final updateDoctorAPI updateDoctorAPI;
    private final getDoctorAPI getDoctorAPI;
    private final getAllDoctorsAPI getAllDoctorsAPI;



    @Autowired
    public DoctorController(addNewDoctorAPI addNewDoctorAPI, deleteDoctorAPI deleteDoctorAPI, getAllDoctorsAPI getAllDoctorsAPI, getDoctorAPI getDoctorAPI,updateDoctorAPI updateDoctorAPI) {
        this.addNewDoctorAPI = addNewDoctorAPI;
        this.deleteDoctorAPI = deleteDoctorAPI;
        this.getAllDoctorsAPI = getAllDoctorsAPI;
        this.getDoctorAPI = getDoctorAPI;
        this.updateDoctorAPI = updateDoctorAPI;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Doctor>> getDoctors() {
        return getAllDoctorsAPI.getAllDoctorsApi();
    }

    @GetMapping("/{docId}")
    public ApiOutput<?> getDoctor(@PathVariable int docId) {
        return getDoctorAPI.getDoctorApi(docId);
    }

    @PostMapping("/add")
    public ApiOutput<?> registerNewDoctor(@Valid @RequestBody Doctor doctor)
    {

        return addNewDoctorAPI.addNewDoctorApi(doctor);

    }

    @DeleteMapping(path = "/{docId}")
    public ApiOutput<?> deleteDoctor(@PathVariable("docId") int id)
    {

        return deleteDoctorAPI.deleteDoctorApi(id);
    }

    @PutMapping(path = "/{docId}")
    public ApiOutput<?> updateDoctor(
            @PathVariable("docId") int docId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Speciality speciality,
            @RequestParam(required = false) Integer fee)
    {
        return updateDoctorAPI.updateDoctorApi(docId,name,email,speciality,fee);
    }


}



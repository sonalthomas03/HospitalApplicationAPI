package com.example.hospital.controller;

import com.example.hospital.api.PatientAPI.*;
import com.example.hospital.entity.Patient;
import com.example.hospital.utilities.ApiOutput;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/hospital/patient")
public class PatientController {

    private final addNewPatientAPI addNewPatientAPI;
    private final getPatientAPI getPatientAPI;
    private final deletePatientAPI deletePatientAPI;
    private final updatePatientAPI updatePatientAPI;
    private final getAllPatientsAPI getAllPatientsAPI;

    @Autowired
    public PatientController(addNewPatientAPI addNewPatientAPI,getPatientAPI getPatientAPI, deletePatientAPI deletePatientAPI, updatePatientAPI updatePatientAPI, getAllPatientsAPI getAllPatientsAPI) {
        this.addNewPatientAPI = addNewPatientAPI;
        this.getPatientAPI = getPatientAPI;
        this.deletePatientAPI = deletePatientAPI;
        this.updatePatientAPI = updatePatientAPI;
        this.getAllPatientsAPI = getAllPatientsAPI;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Patient>> getPatients() {
        return getAllPatientsAPI.getAllPatientsApi();
    }

    @GetMapping("/{patId}")
    public ApiOutput<?> getPatient(@PathVariable int patId) {
        return getPatientAPI.getPatientApi(patId);
    }

    @PostMapping("/add")
    public ApiOutput<?> registerNewPatient(@Valid @RequestBody Patient patient)
    {

        return addNewPatientAPI.addNewPatientApi(patient);

    }

    @DeleteMapping(path = "/{patId}")
    public ApiOutput<?> deletePatient(@PathVariable("patId") int id)
    {

        return deletePatientAPI.deletePatientApi(id);
    }

    @PutMapping(path = "/{patId}")
    public ApiOutput<?> updatePatient(
            @PathVariable("patId") int patId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String ailment)
    {

        return updatePatientAPI.updatePatientApi(patId,name,email,ailment);
    }

}

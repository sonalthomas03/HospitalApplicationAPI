package com.example.hospital.controller;


import com.example.hospital.api.AppointmentAPI.*;
import com.example.hospital.entity.Appointment;
import com.example.hospital.utilities.ApiOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "api/hospital/appointment")
public class AppointmentController {

    private final addNewAppointmentAPI addNewAppointmentAPI;
    private final deleteAppointmentAPI deleteAppointmentAPI;
    private final updateAppointmentAPI updateAppointmentAPI;
    private final getAllAppointmentsAPI getAllAppointmentsAPI;
    private final getAppointmentAPI getAppointmentAPI;

    @Autowired
    public AppointmentController(addNewAppointmentAPI addNewAppointmentAPI, deleteAppointmentAPI deleteAppointmentAPI, updateAppointmentAPI updateAppointmentAPI, getAppointmentAPI getAppointmentAPI, getAllAppointmentsAPI getAllAppointmentsAPI) {
        this.addNewAppointmentAPI = addNewAppointmentAPI;
        this.deleteAppointmentAPI = deleteAppointmentAPI;
        this.updateAppointmentAPI = updateAppointmentAPI;
        this.getAppointmentAPI = getAppointmentAPI;
        this.getAllAppointmentsAPI = getAllAppointmentsAPI;
    }

    @GetMapping( "/all")
    public ResponseEntity<List<Appointment>> getAppointments() {
        return getAllAppointmentsAPI.getAllAppointmentsApi();
    }

    @GetMapping("/{appId}")
    public ApiOutput<?> getAppointment(@PathVariable int appId) {
        return getAppointmentAPI.getAppointmentApi(appId);
    }

    @PostMapping(path = {"{patId}/{docId}",""})
    public ApiOutput<?> registerNewAppointment(@RequestBody Appointment appointment, @PathVariable int docId, @PathVariable int patId)
    {

        return addNewAppointmentAPI.addNewAppointmentApi(appointment,docId,patId);

    }

    @DeleteMapping(path = "/{id}")
    public ApiOutput<?> deleteAppointment(@PathVariable("id") int id)
    {

        return deleteAppointmentAPI.deleteAppointmentApi(id);
    }

    @PutMapping(path = "{id}")
    public ApiOutput<?> updateAppointment(
            @PathVariable("id") int id,
            @RequestParam(required = false) Integer docId,
            @RequestParam(required = false) Integer patId,
            @RequestParam(required = false) LocalDateTime dateTime)
    {

        return updateAppointmentAPI.updateAppointmentApi(id,docId,patId,dateTime);
    }
}

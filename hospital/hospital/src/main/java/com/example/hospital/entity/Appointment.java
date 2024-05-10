package com.example.hospital.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDateTime;


@Data @Entity @Table @AllArgsConstructor @NoArgsConstructor
@EnableWebMvc
public class Appointment {
    @Valid

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Appointment time is mandatory.")
    private LocalDateTime dateTime;

    @NotNull(message = "Doctor is mandatory.")
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @NotNull(message = "Patient is mandatory.")
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;



    public Appointment(LocalDateTime dateTime, Doctor doctor, Patient patient) {
        this.dateTime = dateTime;
        this.doctor = doctor;
        this.patient = patient;
    }





}

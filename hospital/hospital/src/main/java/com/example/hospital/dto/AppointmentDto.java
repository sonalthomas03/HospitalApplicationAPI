package com.example.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class AppointmentDto {
    private DoctorDto doctor;
    private PatientDto patient;
    private LocalDateTime dateTime;
}






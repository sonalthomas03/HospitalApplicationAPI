package com.example.hospital.dto;

import com.example.hospital.utilities.Speciality;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;

@Getter @Setter @NoArgsConstructor
public class DoctorDto {

    private String name;
    private String email;
    @Enumerated(EnumType.STRING) private Speciality speciality;
    private int fee;
    @Transient private transient int workingYears;

    public DoctorDto(String name, String email, Speciality speciality, int fee,LocalDate doj)
    {
        this.name = name;
        this.email = email;
        this.speciality = speciality;
        this.fee = fee;
        this.workingYears = Period.between(doj,LocalDate.now()).getYears();
    }







}

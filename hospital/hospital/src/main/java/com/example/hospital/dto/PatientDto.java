package com.example.hospital.dto;


import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;

@Getter @Setter @NoArgsConstructor
public class PatientDto {

    private String name;
    @Transient
    private transient int age;
    private String ailment;

    public PatientDto(String name, LocalDate dob, String ailment) {
        this.name = name;
        this.age = Period.between(dob, LocalDate.now()).getYears();
        this.ailment = ailment;
    }


}

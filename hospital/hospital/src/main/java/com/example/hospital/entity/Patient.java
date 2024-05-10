package com.example.hospital.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDate;
import java.time.Period;

@Data @Entity @Table @AllArgsConstructor @NoArgsConstructor @EnableWebMvc
public class Patient {
    @Valid
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @NotBlank(message = "Name is mandatory.")
    private String name;
    @NotBlank(message = "email is mandatory.")
    private String email;
    @NotBlank(message = "patientAilment is mandatory.")
    private String patientAilment;
    @NotNull(message = "DOB is mandatory.")
    private LocalDate dob;
    @Transient private transient int age;




    public Patient(String name, String email,String patientAilment, LocalDate dob, int age) {
        this.name = name;
        this.email = email;
        this.patientAilment = patientAilment;
        this.dob = dob;
        this.age = age;
    }

    public Integer getAge() {
        return Period.between(this.dob,LocalDate.now()).getYears();
    }

    public void setAge(Integer age) {
        this.age = age;
    }


}

package com.example.hospital.entity;

import com.example.hospital.utilities.Speciality;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.List;

@Data @Entity @Table @AllArgsConstructor @NoArgsConstructor @EnableWebMvc
public class Doctor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Valid
    @NotBlank(message = "Name is mandatory.")
    private String name;
    @NotBlank(message = "Email is mandatory.")
    private String email;
    @NotNull(message = "DOB is mandatory.")
    private LocalDate dob;
    @NotNull(message = "DOJ is mandatory.")
    private LocalDate doj;
    @NotNull(message = "Speciality is mandatory.")
    @Enumerated(EnumType.STRING) private Speciality speciality;
    @NotNull(message = "Fee is mandatory.")
    private Integer fee;
    @Transient private transient int age;
    @Transient private transient int workingYears;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Appointment> appointments;
    public Doctor(String name, String email, LocalDate dob, LocalDate doj, Speciality speciality,int fee) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.doj = doj;
        this.speciality = speciality;
        this.fee = fee;
    }

    public Integer getAge() {
        return Period.between(this.dob,LocalDate.now()).getYears();
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getWorkingYears() {
        return Period.between(this.doj,LocalDate.now()).getYears();
    }

    public void setWorkingYears(Integer workingYears) {
        this.workingYears = workingYears;
    }


}

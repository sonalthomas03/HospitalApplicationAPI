package com.example.hospital.repository;


import com.example.hospital.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    @Query("SELECT COUNT(a) = 0 FROM Appointment a WHERE a.doctor.id = :docId AND a.dateTime = :dateTime")
    boolean isDoctorAvailable(@Param("docId") int docId, @Param("dateTime") LocalDateTime dateTime);

    @Query("SELECT COUNT(a) > 0 FROM Appointment a WHERE a.patient.id = :patId AND a.dateTime = :dateTime")
    boolean hasPatientAppointmentAtSameTime(@Param("patId") int patId, @Param("dateTime") LocalDateTime dateTime);

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId")
    List<Appointment> findByDoctorId(int doctorId);


}

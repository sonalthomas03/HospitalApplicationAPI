package com.example.hospital.service;


import com.example.hospital.dto.AppointmentDto;
import com.example.hospital.dto.DoctorDto;
import com.example.hospital.dto.PatientDto;
import com.example.hospital.entity.Appointment;
import com.example.hospital.entity.Doctor;
import com.example.hospital.entity.Patient;
import com.example.hospital.exception.exceptions.doctorUnavailableException;
import com.example.hospital.exception.exceptions.entityNotExistException;
import com.example.hospital.exception.exceptions.patientHasAppointmentException;
import com.example.hospital.exception.exceptions.tableEmptyException;
import com.example.hospital.repository.AppointmentRepository;
import com.example.hospital.repository.DoctorRepository;
import com.example.hospital.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private ModelMapper modelMapper;

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> getAllAppointments() {
        if(isAppointmentTableEmpty())
            throw new tableEmptyException("Appointment table is empty");
        return appointmentRepository.findAll();
    }

    public AppointmentDto getAppointment(int id) {
        Appointment appointment =  appointmentRepository.findById(id)
                .orElseThrow(() -> new entityNotExistException("Appointment not found."));

        return new AppointmentDto(modelMapper.map(appointment.getDoctor(),DoctorDto.class),modelMapper.map(appointment.getPatient(),PatientDto.class),appointment.getDateTime());


    }

    public boolean isAppointmentTableEmpty()
    {
        return appointmentRepository.count() == 0;
    }

    public AppointmentDto addNewAppointment(Appointment appointment, int docId,int patId) {
        Doctor doctor = doctorRepository.findById(docId)
                .orElseThrow(() -> new entityNotExistException("Doctor does not exist."));

        Patient patient = patientRepository.findById(patId)
                .orElseThrow(() -> new entityNotExistException("Patient does not exist."));

        LocalDateTime appointmentTime = appointment.getDateTime();

        boolean isBusy =  isDoctorBusyAtDateTime(docId,appointmentTime);
        boolean patientHasAppointment = appointmentRepository.hasPatientAppointmentAtSameTime(patId, appointmentTime);


        if (!isBusy && !patientHasAppointment) {

            appointment.setDoctor(doctor);
            appointment.setPatient(patient);
            appointmentRepository.save(appointment);
        } else if(isBusy && !patientHasAppointment){
            throw new doctorUnavailableException("Doctor is not available at the specified time.");
        }else{
            throw new patientHasAppointmentException("Patient already has an appointment at this time.");
        }

        return new AppointmentDto(modelMapper.map(appointment.getDoctor(),DoctorDto.class),modelMapper.map(appointment.getPatient(),PatientDto.class),appointment.getDateTime());

    }

    public AppointmentDto deleteAppointment(int id)
    {
        boolean exists = appointmentRepository.existsById(id);
        if(!exists)
            throw new entityNotExistException("Appointment does not exist.");

        AppointmentDto deleted = getAppointment(id);
        appointmentRepository.deleteById(id);
        return deleted;

    }

    @Transactional
    public void updateAppointment(int id, Integer docId, Integer patId, LocalDateTime newDateTime) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new entityNotExistException("Appointment does not exist"));
        if(patId!=null)
        {
            Patient patient = patientRepository.findById(patId)
                    .orElseThrow(() -> new entityNotExistException("Patient does not exist"));
            appointment.setPatient(patient);
        }

        if(docId!=null && newDateTime==null)
        {
            Doctor doctor = doctorRepository.findById(docId)
                    .orElseThrow(() -> new entityNotExistException("Doctor does not exist"));
            if (isDoctorBusyAtDateTime(docId,appointment.getDateTime())) {
                throw new doctorUnavailableException("Doctor is not available at the given time.");
            }
            appointment.setDoctor(doctor);
        }
        else if(docId!=null && newDateTime!=null)
        {
            Doctor doctor = doctorRepository.findById(docId)
                    .orElseThrow(() -> new entityNotExistException("Doctor does not exist"));
            if (isDoctorBusyAtDateTime(docId,newDateTime)) {
                throw new doctorUnavailableException("Doctor is not available at the given time.");
            }
            appointment.setDoctor(doctor);
            appointment.setDateTime(newDateTime);
        }
        else if(docId==null && newDateTime!=null)
        {
            Doctor doctor = appointment.getDoctor();
            if (isDoctorBusyAtDateTime(doctor.getId(),newDateTime)) {
                throw new doctorUnavailableException("Doctor is not available at the given time.");
            }
            appointment.setDateTime(newDateTime);
        }
        appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsByDoctorId(int doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    public boolean isDoctorBusyAtDateTime(int doctorId, LocalDateTime dateTime) {
        List<Appointment> doctorAppointments = getAppointmentsByDoctorId(doctorId);

        for (Appointment appointment : doctorAppointments) {
            LocalDateTime appointmentStart = appointment.getDateTime();
            LocalDateTime appointmentEnd = appointmentStart.plusMinutes(30);

            if (dateTime.isAfter(appointmentStart) && dateTime.isBefore(appointmentEnd)) {
                return true;
            }
        }
        return false;
    }

}


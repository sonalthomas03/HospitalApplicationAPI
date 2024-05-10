package com.example.hospital.service;


import com.example.hospital.dto.PatientDto;
import com.example.hospital.entity.Patient;
import com.example.hospital.exception.exceptions.emailAlreadyTakenException;
import com.example.hospital.exception.exceptions.entityNotExistException;
import com.example.hospital.exception.exceptions.tableEmptyException;
import com.example.hospital.repository.PatientRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PatientService {

    @Autowired
    private ModelMapper modelMapper;
    private final PatientRepository patientRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAllPatients() {
        if(isPatientTableEmpty())
            throw new tableEmptyException("Patient table is empty");
        return patientRepository.findAll();
    }

    public PatientDto getPatient(int id) {
        Patient patient=  patientRepository.findById(id)
                .orElseThrow(() -> new entityNotExistException("Patient not found."));

        return modelMapper.map(patient, PatientDto.class);

    }

    public boolean isPatientTableEmpty()
    {
        return patientRepository.count() == 0;
    }

    public PatientDto addNewPatient(Patient patient) {
        Boolean taken = patientRepository.findPatientByEmail(patient.getEmail());
        if(taken)
            throw new emailAlreadyTakenException("Email taken");


        patientRepository.save(patient);

        return modelMapper.map(patient,PatientDto.class);
    }

    public PatientDto deletePatient(int id)
    {
        boolean exists = patientRepository.existsById(id);
        if(!exists)
            throw new entityNotExistException("Patient does not exist.");

        PatientDto deleted = modelMapper.map(patientRepository.findById(id),PatientDto.class);
        patientRepository.deleteById(id);

        return deleted;
    }

    @Transactional
    public void updatePatient(int id,String name,String email,String ailment)
    {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(()->new entityNotExistException(
                        "Patient does not exist"));

        if(name!=null &&
                name.length()>0 &&
                !Objects.equals(patient.getName(),name))
            patient.setName(name);

        if(email!=null && email.length()>0 && !Objects.equals(patient.getEmail(),email)){
            Boolean taken = patientRepository.findPatientByEmail(email);
            if(taken) {
                throw new emailAlreadyTakenException("Email taken");
            }
            patient.setEmail(email);
        }

        if(ailment!=null && ailment.length()>0 && !Objects.equals(patient.getPatientAilment(),ailment))
            patient.setPatientAilment(ailment);

        patientRepository.save(patient);
    }



}

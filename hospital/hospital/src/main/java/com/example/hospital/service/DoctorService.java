package com.example.hospital.service;

import com.example.hospital.dto.DoctorDto;
import com.example.hospital.entity.Doctor;
import com.example.hospital.exception.exceptions.emailAlreadyTakenException;
import com.example.hospital.exception.exceptions.entityNotExistException;
import com.example.hospital.exception.exceptions.invalidSpecialityException;
import com.example.hospital.exception.exceptions.tableEmptyException;
import com.example.hospital.repository.DoctorRepository;
import com.example.hospital.utilities.Speciality;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DoctorService {
    @Autowired
    private ModelMapper modelMapper;

    private final DoctorRepository doctorRepository;
    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }


    public List<Doctor> getAllDoctors() {
        if(isDoctorTableEmpty())
            throw new tableEmptyException("Doctor table is empty");
        return doctorRepository.findAll();
    }

    public DoctorDto getDoctor(int id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new entityNotExistException("Doctor not found."));

        return modelMapper.map(doctor,DoctorDto.class);
    }

    public boolean isDoctorTableEmpty()
    {
        return doctorRepository.count() == 0;
    }

    public DoctorDto addNewDoctor(Doctor doctor)
    {
        Boolean taken = doctorRepository.findDoctorByEmail(doctor.getEmail());
        if(taken)
            throw new emailAlreadyTakenException("Email taken");

        if (!isValidSpecialty(doctor.getSpeciality()))
            throw new invalidSpecialityException("Speciality type is invalid.");
        doctorRepository.save(doctor);

        return modelMapper.map(doctor,DoctorDto.class);
    }

    public DoctorDto deleteDoctor(int docId)
    {
        boolean exists = doctorRepository.existsById(docId);
        if(!exists)
            throw new entityNotExistException("Doctor does not exist.");

        DoctorDto deleted = getDoctor(docId);
        doctorRepository.deleteById(docId);
        return deleted;
    }


    public static boolean isValidSpecialty(Speciality specialty) {
        for (Speciality s : Speciality.values()) {
            if (s.equals(specialty)) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    public void updateDoctor(int docId,String name,String email, Speciality speciality, Integer fee)
    {
        Doctor doctor = doctorRepository.findById(docId)
                .orElseThrow(()->new entityNotExistException(
                        "Doctor does not exist"));

        if(name!=null &&
                name.length()>0 &&
                !Objects.equals(doctor.getName(),name))
            doctor.setName(name);

        if(email!=null && email.length()>0 && !Objects.equals(doctor.getEmail(),email)){
            Boolean taken = doctorRepository.findDoctorByEmail(email);
            if(taken) {
                throw new emailAlreadyTakenException("Email taken");
            }
            doctor.setEmail(email);
        }

        if(speciality!=null && !Objects.equals(doctor.getSpeciality(),speciality))
        {
            if (!isValidSpecialty(speciality))
                throw new invalidSpecialityException("Speciality type not valid");
            doctor.setSpeciality(speciality);
        }

        if(fee!=null && !Objects.equals(doctor.getFee(),fee))
            doctor.setFee(fee);
        doctorRepository.save(doctor);
    }
}

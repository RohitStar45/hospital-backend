package com.hospital.service;

import com.hospital.model.Appointment;
import com.hospital.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class AppointmentService {

    private final AppointmentRepository repository;

    public AppointmentService(AppointmentRepository repository) {
        this.repository = repository;
    }

    public Appointment saveAppointment(Appointment appointment) {
        
        return repository.save(appointment);
    }

    public List<String> getBookedSlotsByDate(LocalDate date) {
    	
        List<Appointment> appointmentsForDate = repository.findByAppointmentDate(date);

        List<String> bookedSlots = new ArrayList<>();

        for (Appointment appointment : appointmentsForDate) {
            bookedSlots.add(appointment.getAppointmentTime());
        }

        return bookedSlots;
    }
    
    public List<Appointment> getAllAppointmentsByDate(LocalDate date) {
        return repository.findByAppointmentDate(date);
    }
    
    public List<Appointment> getAllAppointments() {
        return repository.findAll();
    }
    
    public Optional<Appointment> getAppointmentById(Long id) {
        return repository.findById(id);
    }

    public Appointment updateAppointment(Long id, Appointment updated) {
        Appointment existing = repository.findById(id).orElseThrow();

        existing.setName(updated.getName());
        existing.setMobile(updated.getMobile());
        existing.setMessage(updated.getMessage());
        existing.setAppointmentDate(updated.getAppointmentDate());
        existing.setAppointmentTime(updated.getAppointmentTime());

        return repository.save(existing);
    }
    
    public void deleteAppointmentById(Long id) {
        repository.deleteById(id);
    }


}

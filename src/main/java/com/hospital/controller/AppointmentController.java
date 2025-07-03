package com.hospital.controller;

import com.hospital.model.Appointment;
import com.hospital.service.AppointmentService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @PostMapping("/book-appointment")
    public ResponseEntity<Map<String, String>> bookAppointment(@RequestBody Appointment appointment) {
        
        service.saveAppointment(appointment);

        // Prepare response as a Map, which Spring converts to JSON automatically
        Map<String, String> response = new HashMap<>();
        response.put("msg", "Appointment booked successfully!");

        // Return response with HTTP 200 OK and JSON body
        return ResponseEntity.ok(response);
    }


    @GetMapping("/booked-slots/{date}")
    public ResponseEntity<Map<String, List<String>>> getBookedSlots(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
    	
        List<String> bookedSlots = service.getBookedSlotsByDate(date);

        Map<String, List<String>> response = new HashMap<>();
        response.put("bookedSlots", bookedSlots);

        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/admin/booked-slots/{date}")
    public ResponseEntity<List<Appointment>> getAllAppointments(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        List<Appointment> appointments = service.getAllAppointmentsByDate(date);
        return ResponseEntity.ok(appointments);
    }
    
    @GetMapping("/admin/getallbookedslots")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = service.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }
    
    @PutMapping("/admin/update-booked-slot/{id}")
    public ResponseEntity<Map<String, String>> updateAppointment(
            @PathVariable Long id,
            @RequestBody Appointment updatedAppointment) {
        
        Optional<Appointment> existing = service.getAppointmentById(id);
        if (existing.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("msg", "Appointment not found");
            return ResponseEntity.status(404).body(error);
        }

        service.updateAppointment(id, updatedAppointment);

        Map<String, String> response = new HashMap<>();
        response.put("msg", "Appointment updated successfully!");
        return ResponseEntity.ok(response);
    }

    
    @DeleteMapping("/admin/delete-booked-slot/{id}")
    public ResponseEntity<Map<String, String>> deleteAppointment(@PathVariable Long id) {
        service.deleteAppointmentById(id);

        Map<String, String> response = new HashMap<>();
        response.put("msg", "Appointment deleted successfully!");

        return ResponseEntity.ok(response);
    }


}

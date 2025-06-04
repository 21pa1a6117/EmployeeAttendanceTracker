package com.example.demo.controller;

import com.example.demo.entity.Attendance;
import com.example.demo.entity.Employee;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/mark")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<String> markAttendance(@RequestBody Map<String, String> request) {
        Long empId = Long.parseLong(request.get("employeeId"));
        String status = request.get("status");

        Employee emp = employeeRepository.findById(empId).orElseThrow();
        Attendance att = new Attendance();
        att.setDate(LocalDate.now());
        att.setStatus(status);
        att.setEmployee(emp);
        attendanceRepository.save(att);

        return ResponseEntity.ok("Attendance marked successfully");
    }

    @GetMapping("/report/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public List<Attendance> getAttendance(@PathVariable Long id) {
        return attendanceRepository.findByEmployeeEmployeeId(id);
    }
}
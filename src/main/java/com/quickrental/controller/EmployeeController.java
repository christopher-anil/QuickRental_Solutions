package com.quickrental.controller;

import com.quickrental.model.Employee;
import com.quickrental.model.Rental;
import com.quickrental.model.Vehicle;
import com.quickrental.model.Vehicle.AvailabilityStatus;
import com.quickrental.service.EmployeeService;
import com.quickrental.service.RentalService;
import com.quickrental.service.VehicleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {
    
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private VehicleService vehicleService;
    
    @Autowired
    private RentalService rentalService;
    
    // Show employee login page
    @GetMapping("/emplogin")
    public String showEmployeeLogin() {
        return "emplogin";
    }
    
    // Handle employee login
    @PostMapping("/emplogin")
    public String employeeLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session,
                               Model model) {
        Optional<Employee> employee = employeeService.authenticateEmployee(username, password);
        
        if (employee.isPresent()) {
            session.setAttribute("employeeId", employee.get().getEmpId());
            session.setAttribute("employeeName", employee.get().getEmpName());
            return "redirect:/employee/dashboard";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "emplogin";
        }
    }
    
    // Employee dashboard
    @GetMapping("/employee/dashboard")
    public String employeeDashboard(HttpSession session, Model model) {
        Integer employeeId = (Integer) session.getAttribute("employeeId");
        if (employeeId == null) {
            return "redirect:/emplogin";
        }
        
        List<Vehicle> allVehicles = vehicleService.getAllVehicles();
        List<Vehicle> availableVehicles = vehicleService.getVehiclesByStatus(AvailabilityStatus.AVAILABLE);
        List<Vehicle> bookedVehicles = vehicleService.getVehiclesByStatus(AvailabilityStatus.BOOKED);
        List<Vehicle> rentedVehicles = vehicleService.getVehiclesByStatus(AvailabilityStatus.RENTED);
        List<Vehicle> maintenanceVehicles = vehicleService.getVehiclesByStatus(AvailabilityStatus.MAINTENANCE);
        List<Rental> allRentals = rentalService.getAllRentals();
        
        model.addAttribute("employeeName", session.getAttribute("employeeName"));
        model.addAttribute("allVehicles", allVehicles);
        model.addAttribute("availableVehicles", availableVehicles);
        model.addAttribute("bookedVehicles", bookedVehicles);
        model.addAttribute("rentedVehicles", rentedVehicles);
        model.addAttribute("maintenanceVehicles", maintenanceVehicles);
        model.addAttribute("allRentals", allRentals);
        
        return "employee-dashboard";
    }
    
    // Employee logout
    @GetMapping("/employee/logout")
    public String employeeLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
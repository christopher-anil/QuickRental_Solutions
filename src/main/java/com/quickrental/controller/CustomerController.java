package com.quickrental.controller;

import com.quickrental.model.Customer;
import com.quickrental.model.Rental;
import com.quickrental.model.Vehicle;
import com.quickrental.service.CustomerService;
import com.quickrental.service.RentalService;
import com.quickrental.service.VehicleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private VehicleService vehicleService;
    
    @Autowired
    private RentalService rentalService;
    
    // Show login page
    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }
    
    // Handle login
    @PostMapping("/login")
    public String login(@RequestParam String username, 
                       @RequestParam String password,
                       HttpSession session, 
                       Model model) {
        Optional<Customer> customer = customerService.authenticateCustomer(username, password);
        
        if (customer.isPresent()) {
            session.setAttribute("customerId", customer.get().getUserId());
            session.setAttribute("customerName", customer.get().getFullName());
            return "redirect:/customer/dashboard";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }
    
    // Show registration page
    @GetMapping("/register")
    public String showRegister() {
        return "register";
    }
    
    // Handle registration
    @PostMapping("/register")
    public String register(@RequestParam String full_name,
                          @RequestParam String city,
                          @RequestParam String phone,
                          @RequestParam String email,
                          @RequestParam String driving_licence,
                          @RequestParam String username,
                          @RequestParam String password,
                          Model model) {
        try {
            Customer customer = new Customer(username, password, full_name, 
                                            email, phone, city, driving_licence);
            customerService.registerCustomer(customer);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
    
    // Customer dashboard
    @GetMapping("/customer/dashboard")
    public String customerDashboard(HttpSession session, Model model) {
        Integer customerId = (Integer) session.getAttribute("customerId");
        if (customerId == null) {
            return "redirect:/login";
        }
        
        List<Vehicle> availableVehicles = vehicleService.getAvailableVehicles();
        model.addAttribute("vehicles", availableVehicles);
        model.addAttribute("customerName", session.getAttribute("customerName"));
        
        return "customer-dashboard";
    }
    
    // Show book vehicle page
    @GetMapping("/customer/book/{vehicleId}")
    public String showBookVehicle(@PathVariable Integer vehicleId, 
                                  HttpSession session, 
                                  Model model) {
        Integer customerId = (Integer) session.getAttribute("customerId");
        if (customerId == null) {
            return "redirect:/login";
        }
        
        Optional<Vehicle> vehicle = vehicleService.getVehicleById(vehicleId);
        if (vehicle.isEmpty()) {
            return "redirect:/customer/dashboard";
        }
        
        model.addAttribute("vehicle", vehicle.get());
        model.addAttribute("customerName", session.getAttribute("customerName"));
        return "book-vehicle";
    }
    
    // Handle booking
    @PostMapping("/customer/book")
    public String bookVehicle(@RequestParam Integer vehicleId,
                             @RequestParam String startDate,
                             @RequestParam String endDate,
                             HttpSession session,
                             Model model) {
        Integer customerId = (Integer) session.getAttribute("customerId");
        if (customerId == null) {
            return "redirect:/login";
        }
        
        try {
            Customer customer = customerService.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
            Vehicle vehicle = vehicleService.getVehicleById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
            
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
            
            rentalService.createRental(customer, vehicle, start, end);
            return "redirect:/customer/rentals";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/customer/book/" + vehicleId;
        }
    }
    
    // Show my rentals
    @GetMapping("/customer/rentals")
    public String myRentals(HttpSession session, Model model) {
        Integer customerId = (Integer) session.getAttribute("customerId");
        if (customerId == null) {
            return "redirect:/login";
        }
        
        Customer customer = customerService.findById(customerId)
            .orElseThrow(() -> new RuntimeException("Customer not found"));
        
        List<Rental> rentals = rentalService.getRentalsByCustomer(customer);
        model.addAttribute("rentals", rentals);
        model.addAttribute("customerName", session.getAttribute("customerName"));
        
        return "my-rentals";
    }
    
    // Cancel rental
    @PostMapping("/customer/cancel/{rentalId}")
    public String cancelRental(@PathVariable Integer rentalId, HttpSession session) {
        Integer customerId = (Integer) session.getAttribute("customerId");
        if (customerId == null) {
            return "redirect:/login";
        }
        
        try {
            rentalService.cancelRental(rentalId);
        } catch (Exception e) {
            // Handle error
        }
        
        return "redirect:/customer/rentals";
    }
    
    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
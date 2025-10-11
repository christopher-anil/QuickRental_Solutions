package com.vehicle.rental.rentalsystem.controller;

import com.vehicle.rental.rentalsystem.model.Employee;
import com.vehicle.rental.rentalsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/emplogin")
    public String showEmployeeLogin() {
        return "emplogin";  // This is your login.html
    }

    @PostMapping("/emplogin")
    public String loginEmployee(@RequestParam("username") String username,
                                @RequestParam("password") String password,
                                Model model) {
        Employee emp = employeeService.validateEmployee(username, password);
        if (emp != null) {
            model.addAttribute("employeeName", emp.getEmp_name());
            return "employee_dashboard"; // Redirect to dashboard
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "emplogin";
        }
    }

    @GetMapping("/employee_dashboard")
    public String showDashboard() {
        return "employee_dashboard";
    }
}

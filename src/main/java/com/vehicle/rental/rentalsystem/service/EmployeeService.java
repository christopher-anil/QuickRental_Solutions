package com.vehicle.rental.rentalsystem.service;

import com.vehicle.rental.rentalsystem.model.Employee;
import com.vehicle.rental.rentalsystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee validateEmployee(String email, String password) {
        return employeeRepository.findByEmailAndPassword(email, password);
    }
}

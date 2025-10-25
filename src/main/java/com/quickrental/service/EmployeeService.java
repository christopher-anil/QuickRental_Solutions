package com.quickrental.service;

import com.quickrental.model.Employee;
import com.quickrental.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    public Optional<Employee> authenticateEmployee(String email, String password) {
        Optional<Employee> employee = employeeRepository.findByEmail(email);
        if (employee.isPresent() && employee.get().getPassword().equals(password)) {
            return employee;
        }
        return Optional.empty();
    }
    
    public Optional<Employee> findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }
}
package com.quickrental.service;

import com.quickrental.model.Customer;
import com.quickrental.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    public Customer registerCustomer(Customer customer) {
        // Check if username or email already exists
        if (customerRepository.existsByUsername(customer.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        return customerRepository.save(customer);
    }
    
    public Optional<Customer> authenticateCustomer(String username, String password) {
        Optional<Customer> customer = customerRepository.findByUsername(username);
        if (customer.isPresent() && customer.get().getPassword().equals(password)) {
            return customer;
        }
        return Optional.empty();
    }
    
    public Optional<Customer> findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }
    
    public Optional<Customer> findById(Integer id) {
        return customerRepository.findById(id);
    }
}
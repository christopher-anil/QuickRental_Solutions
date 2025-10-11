package com.vehicle.rental.rentalsystem.service;

import com.vehicle.rental.rentalsystem.model.Customer;
import com.vehicle.rental.rentalsystem.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repo;

    public boolean registerCustomer(Customer customer) {
        if (repo.findByUsername(customer.getUsername()) != null || 
            repo.findByEmail(customer.getEmail()) != null) {
            return false; // already exists
        }
        repo.save(customer);
        return true;
    }

    public boolean validateLogin(String username, String password) {
        Customer c = repo.findByUsername(username);
        return c != null && c.getPassword().equals(password);
    }
}

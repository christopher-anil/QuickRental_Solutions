package com.vehicle.rental.rentalsystem.repository;

import com.vehicle.rental.rentalsystem.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findByEmailAndPassword(String email, String password);
}

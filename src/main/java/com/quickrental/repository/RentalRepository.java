// RentalRepository.java
package com.quickrental.repository;

import com.quickrental.model.Rental;
import com.quickrental.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {
    List<Rental> findByCustomer(Customer customer);
    List<Rental> findByCustomerOrderByStartDateDesc(Customer customer);
}
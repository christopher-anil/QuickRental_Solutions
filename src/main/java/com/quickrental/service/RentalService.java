package com.quickrental.service;

import com.quickrental.model.Customer;
import com.quickrental.model.Rental;
import com.quickrental.model.Rental.RentalStatus;
import com.quickrental.model.Vehicle;
import com.quickrental.model.Vehicle.AvailabilityStatus;
import com.quickrental.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class RentalService {
    
    @Autowired
    private RentalRepository rentalRepository;
    
    @Autowired
    private VehicleService vehicleService;
    
    @Transactional
    public Rental createRental(Customer customer, Vehicle vehicle, 
                              LocalDate startDate, LocalDate endDate) {
        // Check if vehicle is available
        if (vehicle.getAvailabilityStatus() != AvailabilityStatus.AVAILABLE) {
            throw new RuntimeException("Vehicle is not available");
        }
        
        // Calculate total cost
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        if (days <= 0) {
            throw new RuntimeException("Invalid rental period");
        }
        BigDecimal totalCost = vehicle.getRatePerDay().multiply(BigDecimal.valueOf(days));
        
        // Create rental
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setVehicle(vehicle);
        rental.setStartDate(startDate);
        rental.setEndDate(endDate);
        rental.setTotalCost(totalCost);
        rental.setRentalStatus(RentalStatus.BOOKED);
        
        // Update vehicle status
        vehicleService.updateVehicleStatus(vehicle.getVehicleId(), AvailabilityStatus.BOOKED);
        
        return rentalRepository.save(rental);
    }
    
    public List<Rental> getRentalsByCustomer(Customer customer) {
        return rentalRepository.findByCustomerOrderByStartDateDesc(customer);
    }
    
    public List<Rental> getAllRentals() {
        List<Rental> rentals = rentalRepository.findAll();
        rentals.sort((r1, r2) -> r2.getStartDate().compareTo(r1.getStartDate()));
        return rentals;
    }
    
    @Transactional
    public void cancelRental(Integer rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
            .orElseThrow(() -> new RuntimeException("Rental not found"));
        
        if (rental.getRentalStatus() != RentalStatus.BOOKED) {
            throw new RuntimeException("Only booked rentals can be cancelled");
        }
        
        rental.setRentalStatus(RentalStatus.CANCELLED);
        rentalRepository.save(rental);
        
        // Update vehicle status back to available
        vehicleService.updateVehicleStatus(
            rental.getVehicle().getVehicleId(), 
            AvailabilityStatus.AVAILABLE
        );
    }
}
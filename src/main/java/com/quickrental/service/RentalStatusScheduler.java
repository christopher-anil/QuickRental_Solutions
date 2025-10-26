package com.quickrental.service;

import com.quickrental.model.Rental;
import com.quickrental.model.Rental.RentalStatus;
import com.quickrental.model.Vehicle;
import com.quickrental.model.Vehicle.AvailabilityStatus;
import com.quickrental.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class RentalStatusScheduler {
    
    @Autowired
    private RentalRepository rentalRepository;
    
    @Autowired
    private VehicleService vehicleService;
    
    // Run every day at 1:00 AM
    @Scheduled(cron = "0 0 1 * * *")
    @Transactional
    public void updateRentalStatuses() {
        LocalDate today = LocalDate.now();
        List<Rental> allRentals = rentalRepository.findAll();
        
        for (Rental rental : allRentals) {
            // Skip cancelled or completed rentals
            if (rental.getRentalStatus() == RentalStatus.CANCELLED || 
                rental.getRentalStatus() == RentalStatus.COMPLETED) {
                continue;
            }
            
            Vehicle vehicle = rental.getVehicle();
            
            // Check if rental should start today (BOOKED -> RENTED)
            if (rental.getRentalStatus() == RentalStatus.BOOKED && 
                !today.isBefore(rental.getStartDate())) {
                
                rental.setRentalStatus(RentalStatus.RENTED);
                vehicleService.updateVehicleStatus(vehicle.getVehicleId(), AvailabilityStatus.RENTED);
                rentalRepository.save(rental);
                
                System.out.println("Updated rental " + rental.getRentalId() + " to RENTED");
            }
            
            // Check if rental should end (RENTED -> COMPLETED)
            else if (rental.getRentalStatus() == RentalStatus.RENTED && 
                     today.isAfter(rental.getEndDate())) {
                
                rental.setRentalStatus(RentalStatus.COMPLETED);
                vehicleService.updateVehicleStatus(vehicle.getVehicleId(), AvailabilityStatus.AVAILABLE);
                rentalRepository.save(rental);
                
                System.out.println("Updated rental " + rental.getRentalId() + " to COMPLETED");
            }
        }
    }
    
    // Optional: Run every hour for more real-time updates
    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void updateRentalStatusesHourly() {
        updateRentalStatuses();
    }
}
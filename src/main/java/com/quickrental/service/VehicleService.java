package com.quickrental.service;

import com.quickrental.model.Vehicle;
import com.quickrental.model.Vehicle.AvailabilityStatus;
import com.quickrental.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {
    
    @Autowired
    private VehicleRepository vehicleRepository;
    
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }
    
    public List<Vehicle> getAvailableVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findByAvailabilityStatus(AvailabilityStatus.AVAILABLE);
        vehicles.sort((v1, v2) -> {
            int seatCompare = v1.getSeatingCapacity().compareTo(v2.getSeatingCapacity());
            if (seatCompare != 0) {
                return seatCompare;
            }
            return v1.getRatePerDay().compareTo(v2.getRatePerDay());
        });
        return vehicles;
    }
    
    public List<Vehicle> getVehiclesByStatus(AvailabilityStatus status) {
        return vehicleRepository.findByAvailabilityStatus(status);
    }
    
    public Optional<Vehicle> getVehicleById(Integer id) {
        return vehicleRepository.findById(id);
    }
    
    public Vehicle updateVehicleStatus(Integer vehicleId, AvailabilityStatus status) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
            .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        vehicle.setAvailabilityStatus(status);
        return vehicleRepository.save(vehicle);
    }
}
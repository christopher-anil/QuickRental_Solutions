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
    
    public void deleteVehicle(Integer vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
            .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        
        // Only delete if vehicle is AVAILABLE or in MAINTENANCE
        if (vehicle.getAvailabilityStatus() == AvailabilityStatus.BOOKED ||
            vehicle.getAvailabilityStatus() == AvailabilityStatus.RENTED) {
            throw new RuntimeException("Cannot delete vehicle that is booked or rented");
        }
        
        vehicleRepository.delete(vehicle);
    }
    
    public Vehicle addVehicle(String name, String brand, String model, String vehicleType,
                             String fuelType, String transmission, Integer seatingCapacity,
                             Double ratePerDay) {
        Vehicle vehicle = new Vehicle();
        vehicle.setName(name);
        vehicle.setBrand(brand);
        vehicle.setModel(model);
        vehicle.setVehicleType(Vehicle.VehicleType.valueOf(vehicleType));
        vehicle.setFuelType(Vehicle.FuelType.valueOf(fuelType));
        vehicle.setTransmission(Vehicle.Transmission.valueOf(transmission));
        vehicle.setSeatingCapacity(seatingCapacity);
        vehicle.setRatePerDay(java.math.BigDecimal.valueOf(ratePerDay));
        vehicle.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
        
        return vehicleRepository.save(vehicle);
    }
}
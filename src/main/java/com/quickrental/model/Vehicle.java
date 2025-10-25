package com.quickrental.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "vehicle")
public class Vehicle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Integer vehicleId;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(nullable = false, length = 50)
    private String model;
    
    @Column(nullable = false, length = 50)
    private String brand;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", nullable = false)
    private VehicleType vehicleType;
    
    @Column(name = "rate_per_day", nullable = false, precision = 10, scale = 2)
    private BigDecimal ratePerDay;
    
    @Column(name = "seating_capacity", nullable = false)
    private Integer seatingCapacity;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type", nullable = false)
    private FuelType fuelType;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Transmission transmission;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "availability_status", nullable = false)
    private AvailabilityStatus availabilityStatus;
    
    // Enums
    public enum VehicleType {
        BIKE, CAR, SCOOTER
    }
    
    public enum FuelType {
        DIESEL, ELECTRIC, HYBRID, PETROL
    }
    
    public enum Transmission {
        AUTOMATIC, MANUAL
    }
    
    public enum AvailabilityStatus {
        AVAILABLE, BOOKED, MAINTENANCE, RENTED
    }
    
    // Constructors
    public Vehicle() {}
    
    // Getters and Setters
    public Integer getVehicleId() {
        return vehicleId;
    }
    
    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public String getBrand() {
        return brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public VehicleType getVehicleType() {
        return vehicleType;
    }
    
    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }
    
    public BigDecimal getRatePerDay() {
        return ratePerDay;
    }
    
    public void setRatePerDay(BigDecimal ratePerDay) {
        this.ratePerDay = ratePerDay;
    }
    
    public Integer getSeatingCapacity() {
        return seatingCapacity;
    }
    
    public void setSeatingCapacity(Integer seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }
    
    public FuelType getFuelType() {
        return fuelType;
    }
    
    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }
    
    public Transmission getTransmission() {
        return transmission;
    }
    
    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }
    
    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }
    
    public void setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }
}
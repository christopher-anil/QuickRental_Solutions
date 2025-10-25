package com.quickrental.model;

import jakarta.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(name = "full_name", nullable = false)
    private String fullName;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    private String phone;
    
    private String city;
    
    @Column(name = "driving_licence", nullable = false)
    private String drivingLicence;
    
    // Constructors
    public Customer() {}
    
    public Customer(String username, String password, String fullName, 
                   String email, String phone, String city, 
                   String drivingLicence) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.drivingLicence = drivingLicence;
    }
    
    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getDrivingLicence() {
        return drivingLicence;
    }
    
    public void setDrivingLicence(String drivingLicence) {
        this.drivingLicence = drivingLicence;
    }
}
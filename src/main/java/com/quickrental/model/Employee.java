package com.quickrental.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private Integer empId;
    
    @Column(name = "emp_name", nullable = false, length = 100)
    private String empName;
    
    @Column(nullable = false, length = 50)
    private String password;
    
    @Column(nullable = false, unique = true, length = 100)
    private String email;
    
    // Constructors
    public Employee() {}
    
    public Employee(String empName, String password, String email) {
        this.empName = empName;
        this.password = password;
        this.email = email;
    }
    
    // Getters and Setters
    public Integer getEmpId() {
        return empId;
    }
    
    public void setEmpId(Integer empId) {
        this.empId = empId;
    }
    
    public String getEmpName() {
        return empName;
    }
    
    public void setEmpName(String empName) {
        this.empName = empName;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
}
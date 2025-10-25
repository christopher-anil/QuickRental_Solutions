// VehicleRepository.java
package com.quickrental.repository;

import com.quickrental.model.Vehicle;
import com.quickrental.model.Vehicle.AvailabilityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    List<Vehicle> findByAvailabilityStatus(AvailabilityStatus status);
}
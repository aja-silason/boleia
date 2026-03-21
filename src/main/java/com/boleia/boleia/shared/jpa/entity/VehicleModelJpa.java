package com.boleia.boleia.shared.jpa.entity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VehicleModelJpa extends JpaRepository<VehicleModel, String>, JpaSpecificationExecutor<VehicleModel> {
    Optional<VehicleModel> findByPlate(String plate);
    Optional<VehicleModel> findByDriverId(String plate);
    List<VehicleModel> findAllByDriverId(String plate);
}

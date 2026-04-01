package com.boleia.boleia.shared.jpa.entity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DriverModelJpa extends JpaRepository<DriverModel, String>, JpaSpecificationExecutor<DriverModel> {
    Optional<DriverModel> findByIdentificationNumber(String indentificationNumber);
    Optional<DriverModel> findByUserId(String id);
    Optional<DriverModel> findByUserPhoneNumber(String phoneNumber);
}

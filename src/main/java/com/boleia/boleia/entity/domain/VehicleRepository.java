package com.boleia.boleia.entity.domain;

import java.util.UUID;

import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;

public interface VehicleRepository {
    Result<Void, DomainError> save(Vehicle vehicle);
    Result<Vehicle, VehicleNotFoundError> findById(UUID id);
    Result<Vehicle, VehicleNotFoundError> findByPlate(String plate);
}

package com.boleia.boleia.entity.domain;

import java.util.List;

import com.boleia.boleia.shared.types.Result;

public interface VehicleGateway {
    Result<List<VehicleOutput>, Void> findAll();
    Result<List<VehicleOutput>, Void> findAllByDriver(String id);
    Result<VehicleOutput, VehicleNotFoundError> findById(String id);
    Result<VehicleOutput, VehicleNotFoundError> findByDriverId(String id);
    Result<VehicleOutput, VehicleNotFoundError> findByPlate(String plate);
}

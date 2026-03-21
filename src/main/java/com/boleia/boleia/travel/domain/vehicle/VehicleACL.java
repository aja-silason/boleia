package com.boleia.boleia.travel.domain.vehicle;

import java.util.UUID;

import com.boleia.boleia.shared.types.Result;

public interface VehicleACL {
    Result<Vehicle, VehicleNotFoundError> findById(UUID id);
}

package com.boleia.boleia.entity.application;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.boleia.boleia.entity.domain.VehicleRepository;
import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvailableVehicle {
    private final VehicleRepository repository;

    public Result<Void, DomainError> execute(UUID id){
        var vehicleOrErr = this.repository.findById(id);
        if(vehicleOrErr.isError()) return Result.error(vehicleOrErr.unwrapError());

        var vehicle = vehicleOrErr.unwrap();
        vehicle.available();

        var voidOrErr = this.repository.save(vehicle);
        if(voidOrErr.isError()) return Result.error(voidOrErr.unwrapError());

        return Result.ok(null);
    }

}

package com.boleia.boleia.entity.application;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.boleia.boleia.entity.domain.DriverRepository;
import com.boleia.boleia.entity.domain.Vehicle;
import com.boleia.boleia.entity.domain.VehicleIsAlreadyExistsError;
import com.boleia.boleia.entity.domain.VehicleRepository;
import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterVehicle {
    
    private final VehicleRepository repository;
    private final DriverRepository driverRepository;

    public Result<Void, DomainError> execute(RegisterVehicleInput input) {
        var driverOrErr = this.driverRepository.findById(UUID.fromString(input.idDriver()));
        if(driverOrErr.isError()) return Result.error(driverOrErr.unwrapError());

        var vehicleOrErr = this.repository.findByPlate(input.plate());
        if(vehicleOrErr.isOk()) return Result.error(new VehicleIsAlreadyExistsError());

        var vehicle = Vehicle.create(
            input.plate(), 
            input.model(), 
            input.color(), 
            input.brand(), 
            input.seats(), 
            input.year(),
            driverOrErr.unwrap()
        );

        var voidOrErr = this.repository.save(vehicle);
        if(voidOrErr.isError()) return Result.error(voidOrErr.unwrapError());

        return Result.ok(null);
    }

}

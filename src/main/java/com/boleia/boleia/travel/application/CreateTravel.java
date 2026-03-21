package com.boleia.boleia.travel.application;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;
import com.boleia.boleia.travel.domain.Travel;
import com.boleia.boleia.travel.domain.TravelRepository;
import com.boleia.boleia.travel.domain.driver.DriverACL;
import com.boleia.boleia.travel.domain.vehicle.VehicleACL;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateTravel {
    private final TravelRepository repository;
    private final DriverACL driverACL;
    private final VehicleACL vehicleACL;

    @Transactional
    public Result<Void, DomainError> execute(CreateTravelInput input){
        var driverOrErr = this.driverACL.findById(UUID.fromString(input.driverId()));
        if(driverOrErr.isError()) return Result.error(driverOrErr.unwrapError());

        var vehicleOrErr = this.vehicleACL.findById(UUID.fromString(input.vehicleId()));
        if(vehicleOrErr.isError()) return Result.error(vehicleOrErr.unwrapError());

        var travel = Travel.create(
            vehicleOrErr.unwrap().getId(),
            driverOrErr.unwrap().getId(),
            input.dateToTravel(),
            input.price(),
            input.origin(),
            input.destiny(),
            input.seats()
        );

        var voidOrErr = this.repository.save(travel);
        if(voidOrErr.isError()) return Result.error(voidOrErr.unwrapError());

        return Result.ok(null);
    }

}

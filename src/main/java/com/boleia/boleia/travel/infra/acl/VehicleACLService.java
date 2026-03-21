package com.boleia.boleia.travel.infra.acl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.boleia.boleia.shared.jpa.entity.VehicleModel;
import com.boleia.boleia.shared.jpa.entity.VehicleModelJpa;
import com.boleia.boleia.shared.types.Result;
import com.boleia.boleia.travel.domain.vehicle.Vehicle;
import com.boleia.boleia.travel.domain.vehicle.VehicleACL;
import com.boleia.boleia.travel.domain.vehicle.VehicleNotFoundError;

import lombok.RequiredArgsConstructor;

@Service("travels.VehicleACLService")
@RequiredArgsConstructor
public class VehicleACLService implements VehicleACL {
    private final VehicleModelJpa jpa;

    @Override
    public Result<Vehicle, VehicleNotFoundError> findById(UUID id) {
        var model = this.jpa.findById(id.toString());
        return model.isPresent()
            ? Result.ok(this.toVehicleFactory(model.get()))
            : Result.error(new VehicleNotFoundError());
    }

    private Vehicle toVehicleFactory(VehicleModel model){
        return new Vehicle(
            UUID.fromString(model.getId())
        );
    }
    
}

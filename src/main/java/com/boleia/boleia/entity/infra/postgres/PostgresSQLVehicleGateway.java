package com.boleia.boleia.entity.infra.postgres;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.boleia.boleia.entity.domain.VehicleGateway;
import com.boleia.boleia.entity.domain.VehicleNotFoundError;
import com.boleia.boleia.entity.domain.VehicleOutput;
import com.boleia.boleia.shared.jpa.entity.VehicleModel;
import com.boleia.boleia.shared.jpa.entity.VehicleModelJpa;
import com.boleia.boleia.shared.types.Result;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostgresSQLVehicleGateway implements VehicleGateway {
    private final VehicleModelJpa jpa;

    @Override
    public Result<VehicleOutput, VehicleNotFoundError> findById(String id) {
        var model = this.jpa.findById(id);
        return model.isPresent() 
            ? Result.ok(toOutput(model.get())) 
            : Result.error(new VehicleNotFoundError());
    }

    @Override
    public Result<VehicleOutput, VehicleNotFoundError> findByPlate(String plate) {
        var model = this.jpa.findByPlate(plate);
        return model.isPresent() 
            ? Result.ok(toOutput(model.get())) 
            : Result.error(new VehicleNotFoundError());
    }

    @Override
    public Result<List<VehicleOutput>, Void> findAll() {
        var model = this.jpa.findAll();
        var out = model.stream().map(this::toOutput).toList();
        return Result.ok(out);

    }

    private VehicleOutput toOutput(VehicleModel model) {    
        return new VehicleOutput(model.getId(), model.getPlate(), model.getModel(), model.getColor(), model.getBrand(), model.getYear(), model.getSeats(), model.getCreatedAt().toString(), model.getUpdatedAt().toString());
    }

    @Override
    public Result<List<VehicleOutput>, Void> findAllByDriver(String id) {
        var model = this.jpa.findAllByDriverId(id);
        var out = model.stream().map(this::toOutput).toList();
        return Result.ok(out);
    }

    @Override
    public Result<VehicleOutput, VehicleNotFoundError> findByDriverId(String id) {
        var model = this.jpa.findByDriverId(id);
        return model.isPresent() 
            ? Result.ok(toOutput(model.get())) 
            : Result.error(new VehicleNotFoundError());
    }

}

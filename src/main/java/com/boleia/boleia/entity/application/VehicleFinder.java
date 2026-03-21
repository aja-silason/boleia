package com.boleia.boleia.entity.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.boleia.boleia.entity.domain.VehicleGateway;
import com.boleia.boleia.entity.domain.VehicleNotFoundError;
import com.boleia.boleia.entity.domain.VehicleOutput;
import com.boleia.boleia.shared.types.Result;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehicleFinder {
    private final VehicleGateway gateway;

    public Result<List<VehicleOutput>, Void> findAll(){
        return this.gateway.findAll();
    }

    public Result<VehicleOutput, VehicleNotFoundError> findById(String id) {
        return gateway.findById(id);
    }

    public Result<VehicleOutput, VehicleNotFoundError> findByDriver(String id) {
        return gateway.findByDriverId(id);
    }

    public Result<VehicleOutput, VehicleNotFoundError> findByPlate(String plate) {
        return gateway.findByPlate(plate);
    }

    public Result<List<VehicleOutput>, Void> findAllByDriver(String id){
        return this.gateway.findAllByDriver(id);
    }
}

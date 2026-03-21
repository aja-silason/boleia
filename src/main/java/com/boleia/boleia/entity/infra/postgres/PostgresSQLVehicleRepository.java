package com.boleia.boleia.entity.infra.postgres;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.boleia.boleia.entity.domain.Driver;
import com.boleia.boleia.entity.domain.EntityStatus;
import com.boleia.boleia.entity.domain.User;
import com.boleia.boleia.entity.domain.Vehicle;
import com.boleia.boleia.entity.domain.VehicleNotFoundError;
import com.boleia.boleia.entity.domain.VehicleRepository;
import com.boleia.boleia.entity.domain.VehicleStatus;
import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.jpa.entity.DriverModel;
import com.boleia.boleia.shared.jpa.entity.DriverModelJpa;
import com.boleia.boleia.shared.jpa.entity.UserModel;
import com.boleia.boleia.shared.jpa.entity.VehicleModel;
import com.boleia.boleia.shared.jpa.entity.VehicleModelJpa;
import com.boleia.boleia.shared.types.Result;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostgresSQLVehicleRepository implements VehicleRepository {
    
    private final VehicleModelJpa jpa;
    private final DriverModelJpa driverModelJpa;
    
    @Override
    public Result<Void, DomainError> save(Vehicle vehicle) {
        try {
            jpa.save(this.toModel(vehicle));
            return Result.ok(null);
        } catch (Exception e) {
            var msg = "Erro ao salvar veículo no banco de dados: ";
            return Result.error(new DomainError(msg));
        }
    }

    @Override
    public Result<Vehicle, VehicleNotFoundError> findById(UUID id) {
        var model = this.jpa.findById(id.toString());
        return model.isPresent()
            ? Result.ok(this.toVehicleFactory(model.get()))
            : Result.error(new VehicleNotFoundError());
    }

    @Override
    public Result<Vehicle, VehicleNotFoundError> findByPlate(String plate) {
        var model = this.jpa.findByPlate(plate);
        return model.isPresent()
            ? Result.ok(this.toVehicleFactory(model.get()))
            : Result.error(new VehicleNotFoundError());
    }

    private VehicleModel toModel(Vehicle vehicle) {
        var model = (vehicle.getId() != null) 
            ? jpa.findById(vehicle.getId().toString()).orElse(new VehicleModel()) 
            : new VehicleModel();
        model.setPlate(vehicle.getPlate());
        model.setModel(vehicle.getModel());
        model.setColor(vehicle.getColor());
        model.setBrand(vehicle.getBrand());
        model.setYear(vehicle.getSerieYear());
        model.setSeats(vehicle.getSeats());
        model.setStatus(vehicle.getStatus().getValue());
        model.setDriver(this.toDriverModel(vehicle.getDriver()));
        return model;
    }

    private Vehicle toVehicleFactory(VehicleModel model) {
        return Vehicle.from(
            UUID.fromString(model.getId()), 
            model.getPlate(), 
            model.getModel(),
            model.getColor(), 
            model.getBrand(), 
            model.getSeats(), 
            model.getYear(),
            this.toDriverFactory(model.getDriver()),
            VehicleStatus.fromValue(model.getStatus())
        );
    }

    private Driver toDriverFactory(DriverModel model) {
        return Driver.from(
            UUID.fromString(model.getId()), 
            model.getIdentificationNumber(), 
            model.getLicenseNumber(), 
            model.getPassword(), 
            EntityStatus.fromValue(model.getStatus()),
            this.toUserFactory(model.getUser())
        );
    }

    private DriverModel toDriverModel(Driver driver) {
       var model = (driver.getId() != null) 
            ? driverModelJpa.findById(driver.getId().toString()).orElse(new DriverModel()) 
            : new DriverModel();

        model.setId(driver.getId().toString());
        model.setIdentificationNumber(driver.getIdentificationNumber());
        model.setLicenseNumber(driver.getLicenseNumber());
        model.setPassword(driver.getPassword());
        model.setStatus(driver.getStatus().getValue());
        return model;
    }

    private User toUserFactory(UserModel model) {
        return User.from(
            UUID.fromString(model.getId()), 
            model.getFirstName(), 
            model.getLastName(), 
            model.getPhoneNumber(), 
            model.getType(), 
            EntityStatus.fromValue(model.getStatus()));
    }

}

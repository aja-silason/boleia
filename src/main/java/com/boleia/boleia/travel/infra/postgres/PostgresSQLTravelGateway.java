package com.boleia.boleia.travel.infra.postgres;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.boleia.boleia.entity.domain.UserOutput;
import com.boleia.boleia.entity.domain.VehicleOutput;
import com.boleia.boleia.shared.jpa.entity.TravelModel;
import com.boleia.boleia.shared.jpa.entity.TravelModelJpa;
import com.boleia.boleia.shared.jpa.entity.TravelPassangerModel;
import com.boleia.boleia.shared.jpa.entity.TravelQueryBuilder;
import com.boleia.boleia.shared.jpa.entity.UserModel;
import com.boleia.boleia.shared.jpa.entity.UserModelJpa;
import com.boleia.boleia.shared.jpa.entity.VehicleModel;
import com.boleia.boleia.shared.types.Result;
import com.boleia.boleia.travel.domain.PassengerOutput;
import com.boleia.boleia.travel.domain.TravelGateway;
import com.boleia.boleia.travel.domain.TravelNotFoundError;
import com.boleia.boleia.travel.domain.TravelOutput;
import com.boleia.boleia.travel.domain.TravelPassangerStatus;
import com.boleia.boleia.travel.domain.TravelStatus;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostgresSQLTravelGateway implements TravelGateway {
    private final TravelModelJpa jpa;

    @Override
    public Result<List<TravelOutput>, Void> findAll() {
        var model = this.jpa.findAll();
        var out = model.stream().map(this::toOutput).toList();
        return Result.ok(out);
    }

    @Override
    public Result<TravelOutput, TravelNotFoundError> findById(String id) {
        var model = this.jpa.findById(id);
        return model.isPresent()
            ? Result.ok(this.toOutput(model.get()))
            : Result.error(new TravelNotFoundError());
    }

    @Override
    public Result<List<TravelOutput>, Void> findAllDriver(String id) {
        var model = this.jpa.findAllByDriverId(id);
        var out = model.stream().map(this::toOutput).toList();
        return Result.ok(out);
    }

    @Override
    public Result<List<TravelOutput>, Void> findByFilter(String location, Integer seats) {
        var spec = new TravelQueryBuilder()
                        .withAvailableSeats(seats)
                        .withLocation(location)
                        .build();
                        
        var model = this.jpa.findAll(spec);

        var out = model.stream().map(this::toOutput).toList();

        return Result.ok(out);
    }

    private TravelOutput toOutput(TravelModel model) {
        var availablePassangers = model.getPassengers().stream()
            .filter(ps -> ps.getStatus().equals(TravelPassangerStatus.ACCEPTED.getValue()))
            .map(ps -> toPassengerOutput(ps.getPassenger(), ps))
            .toList();

        var pendingPassangers = model.getPassengers().stream()
            .filter(ps -> ps.getStatus().equals(TravelPassangerStatus.PENDING.getValue()))
            .map(ps -> toPassengerOutput(ps.getPassenger(), ps))
            .toList();

        var availableSeats = model.getSeats() - availablePassangers.size();

        var valuePaid = model.getPrice().multiply(new BigDecimal(availablePassangers.size()));

        return new TravelOutput(
            model.getId(),
            UUID.fromString(model.getVehicle().getId()),
            UUID.fromString(model.getDriver().getId()),
            model.getStartTime().toString(),
            TravelStatus.fromValue(model.getStatus()),
            model.getPrice(),
            valuePaid,
            model.getOrigin(),
            model.getDestiny(),
            model.getSeats(),
            availableSeats,
            availablePassangers,
            pendingPassangers,
            null,
            model.getVehicle() == null ? null : this.toCarOutput(model.getVehicle()),
            model.getCreatedAt().toString(),
            model.getUpdatedAt().toString()
        );
    }

    private PassengerOutput toPassengerOutput(UserModel model, TravelPassangerModel travelPassangerModel){
        return new PassengerOutput(
            model.getId(), 
            model.getFirstName(), 
            model.getLastName(), 
            model.getPhoneNumber(), 
            travelPassangerModel.getStatus(), 
            travelPassangerModel.getCreatedAt().toString()
        );
    }

    private UserOutput toDriverOutput(UserModel model) {
        return new UserOutput(
            model.getId().toString(),
            model.getFirstName(),
            model.getLastName(),
            model.getPhoneNumber(),
            true,
            model.getCreatedAt().toString(),
            model.getUpdatedAt().toString()
        );
    }

    private VehicleOutput toCarOutput(VehicleModel model) {
        return new VehicleOutput(
            model.getId().toString(), 
            model.getPlate(), 
            model.getModel(), 
            model.getColor(), 
            model.getBrand(), 
            model.getYear(), 
            model.getSeats(), 
            model.getCreatedAt().toString(),
            model.getUpdatedAt().toString()
        );
    }
    
}

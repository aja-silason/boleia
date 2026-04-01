package com.boleia.boleia.travel.infra.postgres;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.jpa.entity.DriverModel;
import com.boleia.boleia.shared.jpa.entity.TravelPassangerModel;
import com.boleia.boleia.shared.jpa.entity.DriverModelJpa;
import com.boleia.boleia.shared.jpa.entity.TravelModel;
import com.boleia.boleia.shared.jpa.entity.TravelModelJpa;
import com.boleia.boleia.shared.jpa.entity.UserModelJpa;
import com.boleia.boleia.shared.jpa.entity.VehicleModel;
import com.boleia.boleia.shared.jpa.entity.VehicleModelJpa;
import com.boleia.boleia.shared.types.Result;
import com.boleia.boleia.travel.domain.Travel;
import com.boleia.boleia.travel.domain.TravelNotFoundError;
import com.boleia.boleia.travel.domain.TravelPassanger;
import com.boleia.boleia.travel.domain.TravelPassangerStatus;
import com.boleia.boleia.travel.domain.TravelRepository;
import com.boleia.boleia.travel.domain.TravelStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PostgresSQLTravelRepository implements TravelRepository {
    private final TravelModelJpa jpa;
    private final DriverModelJpa driverJpa;
    private final VehicleModelJpa vehicleJpa;
    private final UserModelJpa userJpa;

    @Override
    public Result<Void, DomainError> save(Travel travel) {
        try {
            this.jpa.save(this.toModel(travel));
            return Result.ok(null);
        } catch (Exception e) {
            var msg = "Erro ao salvar a Boleia";
            log.error(msg, e);
            return Result.error(new DomainError(msg));
        }
    }

    @Override
    public Result<Travel, TravelNotFoundError> findById(UUID id) {
        var model = this.jpa.findById(id.toString());
        return model.isPresent()
            ? Result.ok(this.toTravelFactory(model.get()))
            : Result.error(new TravelNotFoundError());
    }

    private TravelModel toModel(Travel travel){
        var model = (travel.getId() != null) ? this.jpa.findById(travel.getId().toString()).orElse(new TravelModel()) : new TravelModel();

        LocalDateTime date = LocalDateTime.parse(travel.getDateToTravel());
        

        List<TravelPassangerModel> passengerModels = travel.getPassangers().stream().map(p -> {
            var pm = new TravelPassangerModel();
            pm.setTravel(model);
            pm.setPassenger(this.userJpa.getReferenceById(p.getPassangerId().toString()));
            pm.setStatus(p.getStatus().getValue());
            return pm;
        }).toList();

        model.getPassengers().clear();
        model.getPassengers().addAll(passengerModels);

        model.setVehicle(this.tovehicleModel(travel.getVehicleId()));
        model.setDriver(this.toDriverModel(travel.getDriverId()));
        model.setStatus(travel.getStatus().getValue());
        model.setStartTime(date);
        model.setPrice(travel.getPrice());
        model.setOrigin(travel.getOrigin());
        model.setDestiny(travel.getDestiny());
        model.setSeats(travel.getSeats());

        return model;
    }

    private DriverModel toDriverModel(UUID id) {
        return this.driverJpa.getReferenceById(id.toString());
    }

    private VehicleModel tovehicleModel(UUID id) {
        return this.vehicleJpa.getReferenceById(id.toString());
    }

    private Travel toTravelFactory(TravelModel model) {
        List<TravelPassanger> passangers = model.getPassengers().stream().map(this::toTravelPassanger).collect(Collectors.toCollection(ArrayList::new));

        return Travel.from(
            UUID.fromString(model.getId()), 
            UUID.fromString(model.getVehicle().getId()), 
            UUID.fromString(model.getDriver().getId()), 
            model.getStartTime().toString(), 
            TravelStatus.fromValue(model.getStatus()), 
            model.getPrice(), 
            model.getOrigin(),
            model.getDestiny(), 
            model.getSeats(),
            passangers
        );
    }

    private TravelPassanger toTravelPassanger(TravelPassangerModel model) {
        return TravelPassanger.from(UUID.fromString(model.getPassenger().getId()), TravelPassangerStatus.fromValue(model.getStatus()));
    }

}
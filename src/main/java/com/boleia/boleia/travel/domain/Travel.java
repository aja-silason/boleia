package com.boleia.boleia.travel.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;

@Getter
public class Travel {
    private UUID id;
    private UUID vehicleId;
    private UUID driverId;
    private String dateToTravel;
    TravelStatus status;
    private BigDecimal price;
    private String origin;
    private String destiny;
    private Integer seats;
    private List<String> stops;
    private List<TravelPassanger> passangers;

    public Travel(
        UUID id,
        UUID vehicleId,
        UUID driverId,
        String dateToTravel,
        TravelStatus status,
        BigDecimal price,
        String origin,
        String destiny,
        Integer seats,
        List<TravelPassanger> passangers
    ) {
        this.id = id;
        this.vehicleId =  vehicleId;
        this.driverId = driverId;
        this.dateToTravel = dateToTravel;
        this.status = status;
        this.price = price;
        this.origin = origin;
        this.destiny = destiny;
        this.seats = seats;
        this.passangers = passangers;
    }

    public static Travel create(
        UUID vehicleId,
        UUID driverId,
        String dateToTravel,
        BigDecimal price,
        String origin,
        String destiny,
        Integer seats
    ) {

        return new Travel(
            UUID.randomUUID(),
            vehicleId, 
            driverId,
            dateToTravel, 
            TravelStatus.OPEN,
            price,
            origin,
            destiny,
            seats,
            new ArrayList<TravelPassanger>()
        );
    }

    public static Travel from(
        UUID id,
        UUID vehicleId,
        UUID driverId,
        String dateToTravel,
        TravelStatus status,
        BigDecimal price,
        String origin,
        String destiny,
        Integer seats,
        List<TravelPassanger> passangers
    ) {
        return new Travel(
            id,
            vehicleId,
            driverId,
            dateToTravel,
            status,
            price,
            origin,
            destiny,
            seats,
            passangers
        );
    }

    public void requestTravel(UUID passangerId){
        this.passangers.add(TravelPassanger.create(passangerId));
    }

    public void finish() {
        this.status = TravelStatus.COMPLETED;
    }

    public void fuel() {
        this.status = TravelStatus.FUEL;
    }

    public void start() {
        this.status = TravelStatus.STARTED;
    }

    public void open(){
        this.status = TravelStatus.OPEN;
    }

    public void acceptPassenger(UUID passengerId) {
        this.passangers.stream()
            .filter(p -> p.getPassangerId().equals(passengerId))
            .findFirst()
            .ifPresent(TravelPassanger::accept);
    }

    public void refusePassenger(UUID passengerId) {
        this.passangers.stream()
            .filter(p -> p.getPassangerId().equals(passengerId))
            .findFirst()
            .ifPresent(TravelPassanger::refused);
    }

}

package com.boleia.boleia.travel.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.boleia.boleia.entity.domain.UserOutput;
import com.boleia.boleia.entity.domain.VehicleOutput;


public record TravelOutput(
    String id,
    UUID vehicleId,
    UUID driverId,
    String dateToTravel,
    TravelStatus status,
    BigDecimal price,
    BigDecimal valuePaid,
    String origin,
    String destiny,
    Integer seats,
    Integer availableSeats,
    List<PassengerOutput> availablePassangers,
    List<PassengerOutput> pendingPassanger,
    UserOutput driver,
    VehicleOutput car,
    String createdAt,
    String updatedAt
) {}

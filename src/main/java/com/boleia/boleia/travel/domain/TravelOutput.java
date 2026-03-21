package com.boleia.boleia.travel.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


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
    String createdAt,
    String updatedAt
) {}

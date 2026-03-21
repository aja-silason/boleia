package com.boleia.boleia.travel.application;

import java.math.BigDecimal;

public record CreateTravelInput(
    String vehicleId,
    String driverId,
    String dateToTravel,
    BigDecimal price,
    String origin,
    String destiny,
    Integer seats
) {}

package com.boleia.boleia.entity.domain;

public record VehicleOutput(
    String id,
    String plate,
    String model,
    String color,
    String brand,
    String year,
    int seats,
    String createdAt,
    String updatedAt
) {}

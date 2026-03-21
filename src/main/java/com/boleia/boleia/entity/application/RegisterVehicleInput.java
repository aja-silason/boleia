package com.boleia.boleia.entity.application;

public record RegisterVehicleInput(
    String idDriver,
    String plate,
    String model,
    String color,
    String brand,
    String seats,
    String year
) {}

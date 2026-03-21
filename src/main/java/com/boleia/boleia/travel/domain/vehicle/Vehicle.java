package com.boleia.boleia.travel.domain.vehicle;

import java.util.UUID;

import lombok.Getter;

@Getter
public class Vehicle {
    private UUID id;

    public Vehicle(
        UUID id
    ) {
        this.id = id;
    }
}
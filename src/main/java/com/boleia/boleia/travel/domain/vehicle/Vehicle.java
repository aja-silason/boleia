package com.boleia.boleia.travel.domain.vehicle;

import java.util.UUID;

import lombok.Getter;

@Getter
public class Vehicle {
    private UUID id;
    private int seats;

    public Vehicle(
        UUID id,
        int seats
    ) {
        this.id = id;
        this.seats = seats;
    }
}
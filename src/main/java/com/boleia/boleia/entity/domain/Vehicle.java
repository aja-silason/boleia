package com.boleia.boleia.entity.domain;

import java.util.UUID;

import lombok.Getter;

@Getter
public class Vehicle {
    private UUID id;
    private String plate;
    private String model;
    private String color;
    private String brand;
    private String seats;
    private String serieYear;
    private Driver driver;
    private VehicleStatus status;

    public Vehicle(
        UUID id,
        String plate,
        String model,
        String color,
        String brand,
        String seats,
        String serieYear,
        Driver driver,
        VehicleStatus status
    ){
        this.id = id;
        this.plate = plate;
        this.model = model;
        this.color = color;
        this.brand = brand;
        this.serieYear = serieYear;
        this.driver = driver;
        this.seats = seats;
        this.status = status;
    }

    public static Vehicle create(
        String plate,
        String model,
        String color,
        String brand,
        String seats,
        String serieYear,
        Driver driver
    ){
        return new Vehicle(
            UUID.randomUUID(),
            plate,
            model,
            color,
            brand,
            seats,
            serieYear,
            driver,
            VehicleStatus.PENDING
        );
    }

    public static Vehicle from(
        UUID id,
        String plate,
        String model,
        String color,
        String brand,
        String seats,
        String serieYear,
        Driver driver,
        VehicleStatus status
    ){
        return new Vehicle(
            id,
            plate,
            model,
            color,
            brand,
            seats,
            serieYear,
            driver,
            status
        );
    }

    public void available(){
        this.status = VehicleStatus.AVAILABLE;
    }

    public boolean isAvailable(){
        return this.status == VehicleStatus.AVAILABLE;
    }

    public void notavailble(){
        this.status = VehicleStatus.NOTAVAILABLE;
    }

    public boolean isNotAvailable(){
        return this.status == VehicleStatus.NOTAVAILABLE;
    }

    public void suspend(){
        this.status = VehicleStatus.SUSPENDED;
    }

    public boolean isSuspend(){
        return this.status == VehicleStatus.SUSPENDED;
    }

}

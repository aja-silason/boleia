package com.boleia.boleia.shared.jpa.entity;

import com.boleia.boleia.shared.jpa.models.BaseModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "vehicles")
public class VehicleModel extends BaseModel {
    @Column(name = "plate")
    private String plate;
    
    @Column(name = "brand", nullable = true)
    private String brand;
    
    @Column(name = "color", nullable = true)
    private String color;

    @Column(name = "model", nullable = true)
    private String model;
    
    @Column(name = "seats")
    private String seats;

    @Column(name = "status")
    private String status;

    @Column(name = "serie_year", nullable = true)
    private String year;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "driver_id")
    private DriverModel driver;
}

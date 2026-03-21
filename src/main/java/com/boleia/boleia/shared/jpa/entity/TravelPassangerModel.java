package com.boleia.boleia.shared.jpa.entity;

import com.boleia.boleia.shared.jpa.models.BaseModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@Entity(name = "travel_passanger")
@NoArgsConstructor
@AllArgsConstructor
public class TravelPassangerModel extends BaseModel {

    @ManyToOne
    @JoinColumn(name = "travel_id")
    private TravelModel travel;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private UserModel passenger;

    @Column(name = "status")
    private String status;

}

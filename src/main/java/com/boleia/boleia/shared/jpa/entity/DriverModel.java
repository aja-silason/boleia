package com.boleia.boleia.shared.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import com.boleia.boleia.shared.jpa.models.BaseModel;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "drivers")
public class DriverModel extends BaseModel {

    @Column(name = "identification_number")
    private String identificationNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
    
    @Column(nullable = false, name = "license_number")
    private String licenseNumber;

    @Column(nullable = false, name = "status")
    private String status;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true, foreignKey = @ForeignKey(name = "fk_driver_user"))
    private UserModel user;

    @OneToMany(mappedBy = "driver", cascade =  CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<VehicleModel> vehicles = new ArrayList<>();

}

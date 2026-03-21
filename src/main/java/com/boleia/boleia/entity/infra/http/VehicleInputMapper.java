package com.boleia.boleia.entity.infra.http;

import org.springframework.stereotype.Component;

import com.boleia.boleia.entity.application.RegisterVehicleInput;

@Component
public class VehicleInputMapper {
    
    public RegisterVehicleInput toRegisterVehicleInput(RegisterVehicleRequest body) {
        return new RegisterVehicleInput(body.idDriver(), body.plate(), body.model(), body.color(), body.brand(), body.seats(), body.serieYear());
    }

}

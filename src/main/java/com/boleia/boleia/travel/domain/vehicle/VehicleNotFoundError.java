package com.boleia.boleia.travel.domain.vehicle;

import com.boleia.boleia.shared.error.DomainError;

public class VehicleNotFoundError extends DomainError {

    public VehicleNotFoundError() {
        super("Veículo não encontrado");
    } 
}

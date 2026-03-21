package com.boleia.boleia.entity.domain;

import com.boleia.boleia.shared.error.DomainError;

public class VehicleIsAlreadyExistsError extends DomainError {
    public VehicleIsAlreadyExistsError() {
        super("Veículo com a mesma placa já existe.");
    }
}

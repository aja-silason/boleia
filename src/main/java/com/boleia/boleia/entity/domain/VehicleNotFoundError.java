package com.boleia.boleia.entity.domain;

import com.boleia.boleia.shared.error.DomainError;

public class VehicleNotFoundError  extends DomainError {
    public VehicleNotFoundError() {
        super("Veiculo não encontrado.");
    }
}

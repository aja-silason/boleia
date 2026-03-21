package com.boleia.boleia.travel.domain.driver;

import com.boleia.boleia.shared.error.DomainError;

public class DriverNotFoundError extends DomainError {

    public DriverNotFoundError() {
        super("Motorista não encontrado");
    }
}

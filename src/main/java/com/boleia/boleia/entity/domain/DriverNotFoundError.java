package com.boleia.boleia.entity.domain;

import com.boleia.boleia.shared.error.DomainError;

public class DriverNotFoundError extends DomainError {

    public DriverNotFoundError() {
        super("Motorista não encontrado");
    }
}

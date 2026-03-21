package com.boleia.boleia.entity.domain;

import com.boleia.boleia.shared.error.DomainError;

public class DriverIsAlreadyExistsError extends DomainError {
    public DriverIsAlreadyExistsError() {
        super("Motorista com o mesmo número de identificação já existe.");
    }
}

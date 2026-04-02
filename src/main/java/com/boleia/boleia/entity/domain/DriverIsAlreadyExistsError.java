package com.boleia.boleia.entity.domain;

import com.boleia.boleia.shared.error.DomainError;

public class DriverIsAlreadyExistsError extends DomainError {
    public DriverIsAlreadyExistsError() {
        super("O motorista com esse número de identificação não pode ser cadastrado.");
    }
}

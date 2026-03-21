package com.boleia.boleia.entity.domain;

import com.boleia.boleia.shared.error.DomainError;

public class PhoneNumberIsAlreadyExistsError extends DomainError {
    public PhoneNumberIsAlreadyExistsError() {
        super("Número de telefone já existe no sistema");
    }
}

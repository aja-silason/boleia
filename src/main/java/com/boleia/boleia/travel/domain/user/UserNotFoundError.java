package com.boleia.boleia.travel.domain.user;

import com.boleia.boleia.shared.error.DomainError;

public class UserNotFoundError extends DomainError {

    public UserNotFoundError() {
        super("Passageiro não encontrado");
    }
}

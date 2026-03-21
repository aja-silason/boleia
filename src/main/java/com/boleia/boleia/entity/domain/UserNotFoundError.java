package com.boleia.boleia.entity.domain;

import com.boleia.boleia.shared.error.DomainError;

public class UserNotFoundError extends DomainError {

    public UserNotFoundError() {
        super("Usuário não encontrado");
    }
}

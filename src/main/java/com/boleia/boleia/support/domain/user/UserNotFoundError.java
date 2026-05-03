package com.boleia.boleia.support.domain.user;

import com.boleia.boleia.shared.error.DomainError;

public class UserNotFoundError extends DomainError {

    public UserNotFoundError() {
        super("Usuário não encontrado");
    }
}

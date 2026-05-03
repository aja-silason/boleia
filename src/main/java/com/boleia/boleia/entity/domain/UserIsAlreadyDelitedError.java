package com.boleia.boleia.entity.domain;

import com.boleia.boleia.shared.error.DomainError;

public class UserIsAlreadyDelitedError extends DomainError {

    public UserIsAlreadyDelitedError() {
        super("Este usuário foi eliminado do sistema");
    }
}

package com.boleia.boleia.entity.domain;

import com.boleia.boleia.shared.error.DomainError;

public class UserIsAlreadyBanedError extends DomainError {
    public UserIsAlreadyBanedError() {
        super("Este usuário foi banido da plataforma");
    }
    
}

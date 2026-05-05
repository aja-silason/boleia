package com.boleia.boleia.entity.domain;

import com.boleia.boleia.shared.error.DomainError;

public class UserIsAlreadyDeclinedError extends DomainError {
    public UserIsAlreadyDeclinedError() {
        super("Este usuário foi removido da plataforma");
    }
    
}

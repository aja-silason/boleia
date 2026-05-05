package com.boleia.boleia.entity.domain;

import com.boleia.boleia.shared.error.DomainError;

public class UserIsAlreadyDeactivatedError extends DomainError {
    public UserIsAlreadyDeactivatedError() {
        super("Este usuário foi desativado da plataforma");
    }
    
}

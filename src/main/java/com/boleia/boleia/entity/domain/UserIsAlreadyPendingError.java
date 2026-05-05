package com.boleia.boleia.entity.domain;

import com.boleia.boleia.shared.error.DomainError;

public class UserIsAlreadyPendingError extends DomainError {
    public UserIsAlreadyPendingError() {
        super("Este usuário está pendente de aprovação");
    }
    
}

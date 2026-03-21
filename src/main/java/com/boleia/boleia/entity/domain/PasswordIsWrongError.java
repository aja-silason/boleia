package com.boleia.boleia.entity.domain;

import com.boleia.boleia.shared.error.DomainError;

public class PasswordIsWrongError extends DomainError {

    public PasswordIsWrongError() {
        super("Senha fornecida não confere com a senha atual");
    }
}

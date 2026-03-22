package com.boleia.boleia.entity.domain;

import com.boleia.boleia.shared.error.DomainError;

public class PasswordLengthError extends DomainError {

    public PasswordLengthError() {
        super("Password must have 6 digits");
    }
}

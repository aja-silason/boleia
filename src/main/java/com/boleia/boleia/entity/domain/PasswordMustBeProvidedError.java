package com.boleia.boleia.entity.domain;

import com.boleia.boleia.shared.error.DomainError;

public class PasswordMustBeProvidedError extends DomainError {

    public PasswordMustBeProvidedError() {
        super("Password must be provided");
    }
}

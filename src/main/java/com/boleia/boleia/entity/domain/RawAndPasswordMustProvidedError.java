package com.boleia.boleia.entity.domain;

import com.boleia.boleia.shared.error.DomainError;

public class RawAndPasswordMustProvidedError extends DomainError {

    public RawAndPasswordMustProvidedError() {
        super("Both raw and hash password must be provided");
    }
}

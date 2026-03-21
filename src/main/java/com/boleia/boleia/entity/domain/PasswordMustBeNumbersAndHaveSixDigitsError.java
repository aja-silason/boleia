package com.boleia.boleia.entity.domain;

import com.boleia.boleia.shared.error.DomainError;

public class PasswordMustBeNumbersAndHaveSixDigitsError extends DomainError {

    public PasswordMustBeNumbersAndHaveSixDigitsError() {
        super("Password must be number and only 6 digits");
    }
}

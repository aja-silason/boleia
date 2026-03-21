package com.boleia.boleia.shared.domain.notification;

import com.boleia.boleia.shared.error.DomainError;

public class PayloadIncorrectError extends DomainError {

    public PayloadIncorrectError() {
        super("Verifica os teus dados e tente novamente");
    }
}

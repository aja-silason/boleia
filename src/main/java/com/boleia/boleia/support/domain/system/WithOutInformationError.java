package com.boleia.boleia.support.domain.system;

import com.boleia.boleia.shared.error.DomainError;

public class WithOutInformationError extends DomainError {

    public WithOutInformationError() {
        super("Sem informações disponíveis");    }
}

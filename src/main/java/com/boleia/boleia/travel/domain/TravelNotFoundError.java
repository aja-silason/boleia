package com.boleia.boleia.travel.domain;

import com.boleia.boleia.shared.error.DomainError;

public class TravelNotFoundError extends DomainError {

    public TravelNotFoundError() {
        super("Viagem não encontrada");
    }
}

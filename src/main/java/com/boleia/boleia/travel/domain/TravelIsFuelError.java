package com.boleia.boleia.travel.domain;

import com.boleia.boleia.shared.error.DomainError;

public class TravelIsFuelError extends DomainError {

    public TravelIsFuelError() {
        super("Viagem está cheia");
    }
}

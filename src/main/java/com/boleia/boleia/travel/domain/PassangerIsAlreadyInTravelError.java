package com.boleia.boleia.travel.domain;

import com.boleia.boleia.shared.error.DomainError;

public class PassangerIsAlreadyInTravelError extends DomainError {

    public PassangerIsAlreadyInTravelError() {
        super("Passageiro já se encontra na viagem");
    }
}

package com.boleia.boleia.travel.domain;

import com.boleia.boleia.shared.error.DomainError;

public class IncommingSeatsIsMoreThanVehiclesSeatsError extends DomainError {
    public IncommingSeatsIsMoreThanVehiclesSeatsError() {
        super("O número de lugares solicitado é maior que os lugares existentes no veiculo.");
    }
    
}

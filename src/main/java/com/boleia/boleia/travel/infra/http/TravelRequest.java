package com.boleia.boleia.travel.infra.http;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TravelRequest(
    @NotNull(message = "Id da corrida precisa ser fornecida")
    @NotBlank(message = "Id da corrida não pode estar vazia")
    String travelId,

    @NotNull(message = "Id do passageiro precisa ser fornecida")
    @NotBlank(message = "Id do passageiro não pode estar vazia")
    String passangerId
) {}

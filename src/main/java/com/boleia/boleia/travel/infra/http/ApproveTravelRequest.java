package com.boleia.boleia.travel.infra.http;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ApproveTravelRequest(
    @NotNull(message = "Id da Boleia precisa ser fornecida")
    @NotBlank(message = "Id da Boleia não pode estar vazia")
    String travelId,

    @NotNull(message = "Id do passageiro precisa ser fornecida")
    @NotBlank(message = "Id do passageiro não pode estar vazia")
    String passangerId
) {}

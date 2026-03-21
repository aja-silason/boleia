package com.boleia.boleia.travel.infra.http;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateTravelRequest(
    @NotNull(message = "Id do veiculo precisa ser fornecida")
    @NotBlank(message = "Id do veiculo não pode estar vazia")
    String vehicleId,

    @NotNull(message = "Id do motorista precisa ser fornecida")
    @NotBlank(message = "Id do motorista não pode estar vazia")
    String driverId,

    @NotNull(message = "Data para viagem precisa ser fornecida")
    @NotBlank(message = "Data para viagem não pode ser vazia")
    String dateToTravel,

    @NotNull(message = "Preço precisa ser fornecida")
    @NotBlank(message = "Preço não pode ser vazia")
    @Positive(message = "Preço precisa ser maior que 0")
    BigDecimal price,

    @NotNull(message = "Origem da corrida precisa ser fornecida")
    @NotBlank(message = "Origem da corrida não pode estar vazia")
    String origin,

    @NotNull(message = "Destino da corrida precisa ser fornecida")
    @NotBlank(message = "Destino da corrida  não pode estar vazia")
    String destiny,

    @NotNull(message = "Lugares vagos na corrida precisa ser fornecida")
    @NotBlank(message = "Lugares vagos não pode estar vazia")
    @Positive(message = "Lugares precisa ser maior que 0")
    Integer seats
) {}

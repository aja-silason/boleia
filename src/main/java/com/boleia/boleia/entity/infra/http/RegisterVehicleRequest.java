package com.boleia.boleia.entity.infra.http;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterVehicleRequest(
    @NotNull(message = "O id do motorista deve ser fornecido")
    @NotBlank(message = "O id do motorista não pode ser vazio")
    String idDriver,

    @NotNull(message = "A placa da matricula do carro deve ser fornecida")
    @NotBlank(message = "A placa da matricula do carro não pode ser vazia")
    String plate,

    @NotNull(message = "O modelo do carro deve ser fornecido")
    @NotBlank(message = "O modelo do carro não pode ser vazio")
    String model,

    @NotNull(message = "A cor do carro deve ser fornecida")
    @NotBlank(message = "A cor do carro não pode estar vazia")
    String color,

    @NotNull(message = "A marca do carro deve ser fornecida")
    @NotBlank(message = "A marca do carro não pode ser vazio")
    String brand,

    @NotNull(message = "Os lugares do carro devem ser fornecidos")
    @NotBlank(message = "Os lugares do carro não podem ser vazios")
    String seats,

    @NotNull(message = "O ano da serie deve ser fornecido")
    @NotBlank(message = "O ano da serie não pode ser vazio")
    String serieYear
) {}

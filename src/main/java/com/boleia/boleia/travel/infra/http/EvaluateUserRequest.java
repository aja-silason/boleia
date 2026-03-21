package com.boleia.boleia.travel.infra.http;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EvaluateUserRequest(
    @NotNull(message = "Id do usuário precisa ser fornecida")
    @NotBlank(message = "Id do usuário não pode estar vazia")
    String userId,
    @NotNull(message = "Valor da avaliação precisa ser fornecida")
    @NotBlank(message = "Valor da avaliação não pode estar vazia")
    @Positive(message = "Valor da avaliação deve ser positivo")
    Integer ratingValue
) {}
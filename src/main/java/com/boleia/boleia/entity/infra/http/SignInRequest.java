package com.boleia.boleia.entity.infra.http;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignInRequest(
    @NotNull(message = "Número de telefone é obrigatório")
    @NotBlank(message = "Número de telefone não pode ser vazio")
    String phoneNumber,

    @NotNull(message = "A senha actual é obrigatório")
    @NotBlank(message = "A senha actual não pode ser vazio")
    String password
) {}

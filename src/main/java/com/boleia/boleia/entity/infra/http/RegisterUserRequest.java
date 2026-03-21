package com.boleia.boleia.entity.infra.http;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterUserRequest(
    @NotNull(message = "Primeiro nome é obrigatório")
    @NotBlank(message = "Primeiro nome não pode ser vazio")
    String firstName,

    @NotNull(message = "Segundo nome é obrigatório")
    @NotBlank(message = "Segundo nome não pode ser vazio")
    String lastName,

    @NotNull(message = "Número de telefone nome é obrigatório")
    @NotBlank(message = "Número de telefone não pode ser vazio")
    String phoneNumber,

    @NotNull(message = "Precisa especificar o tipo de usuario")
    @NotBlank(message = "Tipo de usuario não pode ser vazio")
    boolean isDriver
) {}

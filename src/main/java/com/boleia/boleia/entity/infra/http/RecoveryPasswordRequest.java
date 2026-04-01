package com.boleia.boleia.entity.infra.http;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RecoveryPasswordRequest(
    @NotNull(message = "Número de celular é obrigatório")
    @NotBlank(message = "Número de celular não pode ser vazio")
    String phoneNumber,

    @NotNull(message = "Senha é obrigatório")
    @NotBlank(message = "Senha não pode ser vazio")
    String password
    
) {}

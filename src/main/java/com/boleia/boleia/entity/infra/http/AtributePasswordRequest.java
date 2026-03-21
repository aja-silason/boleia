package com.boleia.boleia.entity.infra.http;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtributePasswordRequest(
    @NotNull(message = "Número de identificação é obrigatório")
    @NotBlank(message = "Número de identificação não pode ser vazio")
    String identificationNumber,

    @NotNull(message = "Senha é obrigatório")
    @NotBlank(message = "Senha não pode ser vazio")
    String password
    
) {}

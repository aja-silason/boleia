package com.boleia.boleia.entity.infra.http;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDriverRequest(
    @NotNull(message = "Primeiro nome é obrigatório")
    @NotBlank(message = "Primeiro nome não pode ser vazio")
    String firstName,

    @NotNull(message = "Último nome é obrigatório")
    @NotBlank(message = "Último nome não pode ser vazio")
    String lastName,

    @NotNull(message = "Telefone é obrigatório")
    @NotBlank(message = "Telefone não pode ser vazio")
    String phoneNumber,

    @NotNull(message = "Tipo de usuário é obrigatório")
    @NotBlank(message = "Tipo de usuário não pode ser vazio")
    boolean isDriver,
    
    @NotNull(message = "Número de identificação é obrigatório")
    @NotBlank(message = "Número de identificação não pode ser vazio")
    String identificationNumber,

    @NotNull(message = "Número da licença é obrigatório")
    @NotBlank(message = "Número da licença não pode ser vazio")
    String licenseNumber
) {}

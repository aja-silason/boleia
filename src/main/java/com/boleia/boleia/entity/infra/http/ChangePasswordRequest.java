package com.boleia.boleia.entity.infra.http;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChangePasswordRequest(
    @NotNull(message = "driver id é obrigatório")
    @NotBlank(message = "driver id não pode ser vazio")
    String driverId,

    @NotNull(message = "A senha actual é obrigatório")
    @NotBlank(message = "A senha actual não pode ser vazio")
    String oldPassword,

    @NotNull(message = "Senha de confirmação é obrigatório")
    @NotBlank(message = "Senha de confirmação não pode ser vazio")
    String confirmedPassword
) {}

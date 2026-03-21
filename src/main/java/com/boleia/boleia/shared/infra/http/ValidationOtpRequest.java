package com.boleia.boleia.shared.infra.http;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ValidationOtpRequest(
    @NotNull(message = "Número de telefone deve ser fornecido")
    @NotBlank(message = "Número de telefone não pode ser vazio")
    String phoneNumber,

    @NotNull(message = "Precisa fornecer o OTP")
    @NotBlank(message = "OTP não pode ser vazio")
    String otp
) {}

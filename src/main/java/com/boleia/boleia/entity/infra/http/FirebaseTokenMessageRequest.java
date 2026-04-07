package com.boleia.boleia.entity.infra.http;

import jakarta.validation.constraints.NotEmpty;

public record FirebaseTokenMessageRequest(
    @NotEmpty(message = "Id do usuário não pode estar vazio")
    String userId,

    @NotEmpty(message = "fcm não deve estar vazio")
    String fcm
) {}

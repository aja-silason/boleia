package com.boleia.boleia.support.application;

import java.util.UUID;

public record RequestSupportInput(
    UUID userId,
    String message
) {}

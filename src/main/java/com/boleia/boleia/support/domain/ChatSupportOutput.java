package com.boleia.boleia.support.domain;

import java.time.LocalDateTime;

public record ChatSupportOutput(
    String id,
    String message,
    String username,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
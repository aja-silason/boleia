package com.boleia.boleia.support.domain.politicsAndTerms;

import java.time.LocalDateTime;

public record TermsAndPolitcsOutput(
    String id,
    String title,
    String description,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}

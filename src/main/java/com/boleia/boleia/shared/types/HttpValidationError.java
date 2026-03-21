package com.boleia.boleia.shared.types;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Map;

public record HttpValidationError(
    @Schema(description = "The error message.") String message,

    @Schema(description = "The date and time when the error happened.")
    LocalDateTime timestamp,

    @Schema(description = "Fields of validation error") Map fields,

    @Schema(description = "The error code") int code
) {}

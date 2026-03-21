package com.boleia.boleia.shared.types;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public record HttpResponse(
    @Schema(description = "The error message.")
    String message,

    @Schema(description = "The date and time when the error happened.")
    LocalDateTime timestamp,

    @Schema(description = "The error code")
    int code
) {
    public static <T> ResponseEntity<T> ok(T body) {
        return ResponseEntity.ok(body);
    }

    public static ResponseEntity<HttpResponse> ok(String message) {
        return ResponseEntity.ok(
            new HttpResponse(
                message,
                LocalDateTime.now(),
                HttpStatus.OK.value()
            )
        );
    }

    public static <T> ResponseEntity<T> created(T body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    public static ResponseEntity<HttpResponse> badRequest(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            new HttpResponse(
                message,
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value()
            )
        );
    }

    public static ResponseEntity<HttpValidationError> badRequest(Map errors) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            new HttpValidationError(
                "Validation Error",
                LocalDateTime.now(),
                errors,
                HttpStatus.BAD_REQUEST.value()
            )
        );
    }

    public static ResponseEntity<HttpResponse> forbidden(String message) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
            new HttpResponse(
                message,
                LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value()
            )
        );
    }

    public static ResponseEntity<HttpResponse> notFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            new HttpResponse(
                message,
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value()
            )
        );
    }

    public static ResponseEntity<HttpResponse> conflict(String message) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            new HttpResponse(
                message,
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value()
            )
        );
    }

    public static ResponseEntity<HttpResponse> serviceUnavailable(
        String message
    ) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .header("Retry-After", "300")
            .body(
                new HttpResponse(
                    message,
                    LocalDateTime.now(),
                    HttpStatus.SERVICE_UNAVAILABLE.value()
                )
            );
    }

    public static ResponseEntity<HttpResponse> serverError(String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            new HttpResponse(
                message,
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
            )
        );
    }

    public static ResponseEntity<HttpResponse> noContent() {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
            new HttpResponse(
                "Sem conteúdo",
                LocalDateTime.now(),
                HttpStatus.NO_CONTENT.value()
            )
        );
    }

    public static ResponseEntity<HttpResponse> from(
        HttpStatus status,
        String msg
    ) {
        return ResponseEntity.status(status).body(
            new HttpResponse(msg, LocalDateTime.now(), status.value())
        );
    }
}

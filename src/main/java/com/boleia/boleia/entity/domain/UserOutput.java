package com.boleia.boleia.entity.domain;

public record UserOutput(
    String id,
    String fcmUserId,
    String firstName,
    String lastName,
    String phoneNumber,
    boolean isDriver,
    String createdAt,
    String updatedAt
) {}

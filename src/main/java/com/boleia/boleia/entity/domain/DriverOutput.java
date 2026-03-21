package com.boleia.boleia.entity.domain;

public record DriverOutput(
    String id,
    String identificationNumber,
    String licenseNumber,
    EntityStatus status,
    UserOutput user,
    String createdAt,
    String updatedAt
) {}

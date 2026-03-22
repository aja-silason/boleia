package com.boleia.boleia.entity.domain;

public record SignInOutput(
    String id,
    String firstName,
    String lastName,
    String phoneNumber,
    String identificationNumber,
    String licenseNumber,
    String status,
    String userWillBeSignedUntil
) {}

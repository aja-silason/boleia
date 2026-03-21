package com.boleia.boleia.entity.application;

public record RegisterDriverInput(
    String firstName,
    String lastName,
    String phoneNumber,
    boolean isDriver,
    String identificationNumber,
    String licenseNumber
) {}

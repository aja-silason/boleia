package com.boleia.boleia.travel.domain;

public record PassengerOutput(
    String id,
    String firstName,
    String lastName,
    String phoneNumber,
    String status,
    String requestedAt
) {}

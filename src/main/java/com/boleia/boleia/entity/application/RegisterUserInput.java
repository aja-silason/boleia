package com.boleia.boleia.entity.application;

public record RegisterUserInput(
    String firstName,
    String lastName,
    String phoneNumber,
    boolean isDriver
) {}

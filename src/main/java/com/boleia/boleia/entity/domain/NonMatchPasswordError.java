package com.boleia.boleia.entity.domain;

import com.boleia.boleia.shared.error.DomainError;

public class NonMatchPasswordError extends DomainError {

    public NonMatchPasswordError() {
        super("Password fornecidas são diferentes");
    }
}

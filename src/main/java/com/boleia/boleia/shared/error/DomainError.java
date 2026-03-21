package com.boleia.boleia.shared.error;

import lombok.Getter;

@Getter
public class DomainError {

    private final String msg;

    public DomainError(String msg) {
        this.msg = msg;
    }
}

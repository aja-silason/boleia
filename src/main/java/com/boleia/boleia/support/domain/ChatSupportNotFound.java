package com.boleia.boleia.support.domain;

import com.boleia.boleia.shared.error.DomainError;

public class ChatSupportNotFound  extends DomainError {

    public ChatSupportNotFound() {
        super("Reporte não encontrado");
    }
}

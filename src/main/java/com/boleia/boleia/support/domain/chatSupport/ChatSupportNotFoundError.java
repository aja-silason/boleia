package com.boleia.boleia.support.domain.chatSupport;

import com.boleia.boleia.shared.error.DomainError;

public class ChatSupportNotFoundError  extends DomainError {

    public ChatSupportNotFoundError() {
        super("Reporte não encontrado");
    }
}

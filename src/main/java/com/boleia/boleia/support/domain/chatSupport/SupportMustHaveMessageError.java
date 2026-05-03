package com.boleia.boleia.support.domain.chatSupport;

import com.boleia.boleia.shared.error.DomainError;

public class SupportMustHaveMessageError extends DomainError {

    public SupportMustHaveMessageError() {
        super("A mensagem não pode estar vazia");    }
    
}

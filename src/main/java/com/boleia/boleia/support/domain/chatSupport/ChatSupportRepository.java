package com.boleia.boleia.support.domain.chatSupport;

import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;

public interface ChatSupportRepository {
    Result<Void, DomainError> save(ChatSupport chatSupport);
}

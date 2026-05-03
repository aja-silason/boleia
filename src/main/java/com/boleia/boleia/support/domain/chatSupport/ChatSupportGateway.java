package com.boleia.boleia.support.domain.chatSupport;

import java.util.List;

import com.boleia.boleia.shared.types.Result;

public interface ChatSupportGateway{
    Result<List<ChatSupportOutput>, Void> findAll();
    Result<ChatSupportOutput, ChatSupportNotFoundError> findById(String id);
}

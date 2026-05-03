package com.boleia.boleia.support.domain;

import java.util.List;
import java.util.UUID;

import com.boleia.boleia.shared.types.Result;

public interface ChatSupportGateway{
    Result<List<ChatSupportOutput>, Void> findAll();
    Result<ChatSupportOutput, ChatSupportNotFound> findById(UUID id);
}

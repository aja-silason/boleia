package com.boleia.boleia.support.infra.postgres;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.boleia.boleia.shared.jpa.entity.ChatSupportModel;
import com.boleia.boleia.shared.jpa.entity.ChatSupportModelJpa;
import com.boleia.boleia.shared.types.Result;
import com.boleia.boleia.support.domain.chatSupport.ChatSupportGateway;
import com.boleia.boleia.support.domain.chatSupport.ChatSupportNotFoundError;
import com.boleia.boleia.support.domain.chatSupport.ChatSupportOutput;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostgreSQLChatSupportGateway implements ChatSupportGateway {

    private final ChatSupportModelJpa jpa;

    @Override
    public Result<List<ChatSupportOutput>, Void> findAll() {
        var model = this.jpa.findAll();
        var out = model.stream().map(this::toOutput).toList();
        return Result.ok(out);
    }

    @Override
    public Result<ChatSupportOutput, ChatSupportNotFoundError> findById(String id) {
        var model = this.jpa.findById(id);
        return model.isPresent()
            ? Result.ok(this.toOutput(model.get()))
            : Result.error(new ChatSupportNotFoundError());
    }

    private ChatSupportOutput toOutput(ChatSupportModel model) {
        return new ChatSupportOutput(
            model.getId(),
            model.getMessage(),
            model.getUser() != null ? model.getUser().getFirstName() +' '+ model.getUser().getLastName() : null,
            model.getCreatedAt(),
            model.getUpdatedAt()
        );
    }

}

package com.boleia.boleia.support.infra.postgres;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.jpa.entity.ChatSupportModel;
import com.boleia.boleia.shared.jpa.entity.ChatSupportModelJpa;
import com.boleia.boleia.shared.jpa.entity.UserModel;
import com.boleia.boleia.shared.jpa.entity.UserModelJpa;
import com.boleia.boleia.shared.types.Result;
import com.boleia.boleia.support.domain.chatSupport.ChatSupport;
import com.boleia.boleia.support.domain.chatSupport.ChatSupportRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostgreSQLChatSupportRepository implements ChatSupportRepository {

    private final ChatSupportModelJpa jpa;
    private final UserModelJpa useJpa;

    @Override
    public Result<Void, DomainError> save(ChatSupport chatSupport) {
        try {
            jpa.save(this.toModel(chatSupport));
            return Result.ok(null);
        } catch (Exception e) {
            var msg = "Erro ao reportar";
            return Result.error(new DomainError(msg));
        }
    }

    private ChatSupportModel toModel(ChatSupport domain) {
        var model = new ChatSupportModel();
        model.setId(domain.getId().toString());
        model.setMessage(domain.getMessage());
        model.setUser(this.toUserModel(domain.getUserId()));
        return model;

    }

    private UserModel toUserModel(UUID id) {
        return this.useJpa.getReferenceById(id.toString());
    }


}

package com.boleia.boleia.support.application;

import org.springframework.stereotype.Service;

import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;
import com.boleia.boleia.support.domain.chatSupport.ChatSupport;
import com.boleia.boleia.support.domain.chatSupport.ChatSupportRepository;
import com.boleia.boleia.support.domain.chatSupport.SupportMustHaveMessageError;
import com.boleia.boleia.support.domain.user.UserACL;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RequestSupport {
    private final ChatSupportRepository repository;
    private final UserACL userACL;

    public Result<Void, DomainError> execute(RequestSupportInput input){
        var userOrErr = this.userACL.findById(input.userId());
        if(userOrErr.isError()) return Result.error(userOrErr.unwrapError());

        if(input.message().isEmpty() || input.message().isBlank()) return Result.error(new SupportMustHaveMessageError());

        var support = ChatSupport.create(input.userId(), input.message());

        var saveOrErr = this.repository.save(support);
        if(saveOrErr.isError()) return Result.error(saveOrErr.unwrapError());

        return Result.ok(null);
    }

}

package com.boleia.boleia.entity.application;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.boleia.boleia.entity.domain.UserNotFoundError;
import com.boleia.boleia.entity.domain.UserRepository;
import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeactiveUser {
    private final UserRepository userRepository;

    @Transactional
    public Result<Void, DomainError> execute(UUID id){
        var userOrErr = this.userRepository.findById(id);
        if(userOrErr.isError()) return Result.error(new UserNotFoundError());

        var user = userOrErr.unwrap();

        user.deactive();;

        var voidOrErr = this.userRepository.save(user);
        if(voidOrErr.isError()) return Result.error(voidOrErr.unwrapError());

        return Result.ok(null);
    }

}

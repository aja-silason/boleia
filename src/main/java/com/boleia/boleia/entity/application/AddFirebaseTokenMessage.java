package com.boleia.boleia.entity.application;

import java.util.UUID;

import com.boleia.boleia.entity.domain.UserRepository;
import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddFirebaseTokenMessage {
    private UserRepository userRepository;

    public Result<Void, DomainError> execute(FirebaseTokenMessageInput input){
        var userOrErr = this.userRepository.findById(UUID.fromString(input.usrId()));
        if(userOrErr.isError()) return Result.error(userOrErr.unwrapError());

        var user = userOrErr.unwrap();

        user.addFCM(input.fcm());

        var saveOrErr = this.userRepository.save(user);
        if(saveOrErr.isError()) return Result.error(saveOrErr.unwrapError());

        return Result.ok(null);
    }

}

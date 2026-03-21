package com.boleia.boleia.entity.application;

import org.springframework.stereotype.Service;

import com.boleia.boleia.entity.domain.EntityType;
import com.boleia.boleia.entity.domain.PhoneNumberIsAlreadyExistsError;
import com.boleia.boleia.entity.domain.User;
import com.boleia.boleia.entity.domain.UserRepository;
import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegisterUser {
    private final UserRepository repository;

    public Result<Void, DomainError> execute(RegisterUserInput input){
        
        var userOrErr = this.repository.findByPhoneNumber(input.phoneNumber());
        if(userOrErr.isOk()) return Result.error(new PhoneNumberIsAlreadyExistsError());

        var user = User.create(
            input.firstName(),
            input.lastName(),
            input.phoneNumber(),
            EntityType.PASSANGER
        );

        var saveOrErr = this.repository.save(user);
        if(saveOrErr.isError()) return Result.error(saveOrErr.unwrapError());

        return Result.ok(null);

    }

}

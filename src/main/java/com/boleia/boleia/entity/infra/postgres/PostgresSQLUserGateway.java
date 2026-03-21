package com.boleia.boleia.entity.infra.postgres;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.boleia.boleia.entity.domain.UserGateway;
import com.boleia.boleia.entity.domain.UserNotFoundError;
import com.boleia.boleia.entity.domain.UserOutput;
import com.boleia.boleia.shared.jpa.entity.UserModel;
import com.boleia.boleia.shared.jpa.entity.UserModelJpa;
import com.boleia.boleia.shared.types.Result;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PostgresSQLUserGateway implements UserGateway {
    private final UserModelJpa jpa;
    
    @Override
    public Result<UserOutput, UserNotFoundError> findByPhoneNumber(String phoneNumber) {
        var model = this.jpa.findByPhoneNumber(phoneNumber);
        return model.isPresent() 
            ? Result.ok(this.toUserOutput(model.get())) 
            : Result.error(new UserNotFoundError());
    }

    @Override
    public Result<UserOutput, UserNotFoundError> findById(String id) {
        var model = this.jpa.findById(id);
        return model.isPresent() 
            ? Result.ok(this.toUserOutput(model.get())) 
            : Result.error(new UserNotFoundError());
    }

    @Override
    public Result<List<UserOutput>, Void> findAll() {
        var models = this.jpa.findAll();
        var out = models.stream().map(this::toUserOutput).toList();
        return Result.ok(out);
    }

    private UserOutput toUserOutput(UserModel model) {
        return new UserOutput(
            model.getId().toString(),
            model.getFirstName(),
            model.getLastName(),
            model.getPhoneNumber(),
            model.isDriver(),
            model.getCreatedAt().toString(),
            model.getUpdatedAt().toString()
        );
    }

}

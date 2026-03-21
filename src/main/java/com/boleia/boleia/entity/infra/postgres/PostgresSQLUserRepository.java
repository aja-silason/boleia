package com.boleia.boleia.entity.infra.postgres;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.boleia.boleia.entity.domain.EntityStatus;
import com.boleia.boleia.entity.domain.User;
import com.boleia.boleia.entity.domain.UserNotFoundError;
import com.boleia.boleia.entity.domain.UserRepository;
import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.jpa.entity.UserModel;
import com.boleia.boleia.shared.jpa.entity.UserModelJpa;
import com.boleia.boleia.shared.types.Result;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PostgresSQLUserRepository implements UserRepository {

    private final UserModelJpa jpa;

    @Override
    public Result<Void, DomainError> save(User user) {

        try {
            jpa.save(this.toModel(user));
            return Result.ok(null);
        } catch (Exception e) {
            var msg = "Erro ao salvar usuário no banco de dados: ";
            log.error(msg, e);
            return Result.error(new DomainError(msg));
        }
    }

    @Override
    public Result<User, UserNotFoundError> findByPhoneNumber(String phoneNumber) {
       var model = this.jpa.findByPhoneNumber(phoneNumber);
        return model.isPresent() 
            ? Result.ok(this.toUserFactory(model.get())) 
            : Result.error(new UserNotFoundError());
    }

    @Override
    public Result<User, UserNotFoundError> findById(UUID id) {
        var model = this.jpa.findById(id.toString());
        return model.isPresent() 
            ? Result.ok(this.toUserFactory(model.get())) 
            : Result.error(new UserNotFoundError());

    }

    private UserModel toModel(User user) {
        UserModel model = (user.getId() != null) 
        ? jpa.findById(user.getId().toString()).orElse(new UserModel()) 
        : new UserModel();

        model.setId(user.getId().toString());
        model.setFirstName(user.getFirstName());
        model.setLastName(user.getLastName());
        model.setPhoneNumber(user.getPhoneNumber());
        model.setType(user.getType());
        model.setStatus(user.getStatus().getValue());
        return model;
    }

    private User toUserFactory(UserModel model) {
        return User.from(
            UUID.fromString(model.getId()), 
            model.getFirstName(), 
            model.getLastName(), 
            model.getPhoneNumber(), 
            model.getType(), 
            EntityStatus.fromValue(model.getStatus()));
    }

}

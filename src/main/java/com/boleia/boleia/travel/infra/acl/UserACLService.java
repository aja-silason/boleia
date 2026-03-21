package com.boleia.boleia.travel.infra.acl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.boleia.boleia.shared.jpa.entity.UserModel;
import com.boleia.boleia.shared.jpa.entity.UserModelJpa;
import com.boleia.boleia.shared.types.Result;
import com.boleia.boleia.travel.domain.user.EntityType;
import com.boleia.boleia.travel.domain.user.User;
import com.boleia.boleia.travel.domain.user.UserACL;
import com.boleia.boleia.travel.domain.user.UserNotFoundError;

import lombok.RequiredArgsConstructor;

@Service("vehicle.UserACL")
@RequiredArgsConstructor
public class UserACLService implements UserACL {
    private final UserModelJpa jpa;

    @Override
    public Result<User, UserNotFoundError> findById(UUID id) {
        var model = this.jpa.findById(id.toString());
        return model.isPresent()
            ? Result.ok(this.toUserFactory(model.get()))
            : Result.error(new UserNotFoundError());
    }

    private User toUserFactory(UserModel model){
        return new User(
            UUID.fromString(model.getId()),
            EntityType.fromValue(model.getType().toString())
        );
    }

}

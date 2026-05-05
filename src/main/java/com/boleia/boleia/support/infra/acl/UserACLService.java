package com.boleia.boleia.support.infra.acl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.boleia.boleia.shared.jpa.entity.UserModel;
import com.boleia.boleia.shared.jpa.entity.UserModelJpa;
import com.boleia.boleia.shared.types.Result;
import com.boleia.boleia.support.domain.user.User;
import com.boleia.boleia.support.domain.user.UserAcl;
import com.boleia.boleia.support.domain.user.UserNotFoundError;

import lombok.RequiredArgsConstructor;

@Service("support.UserACL")
@RequiredArgsConstructor
public class UserACLService implements UserAcl {
    private final UserModelJpa jpa;

    @Override
    public Result<User, UserNotFoundError> findById(UUID id) {
        var model = this.jpa.findById(id.toString());
        return model.isPresent()
            ? Result.ok(this.toUserFactory(model.get()))
            : Result.error(null);
    }

    public User toUserFactory(UserModel model){
        return new User(
            UUID.fromString(model.getId()),
            model.getFirstName(),
            model.getLastName()
        );
    }

}

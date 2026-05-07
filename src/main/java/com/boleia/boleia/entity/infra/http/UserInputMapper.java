package com.boleia.boleia.entity.infra.http;

import org.springframework.stereotype.Component;

import com.boleia.boleia.entity.application.AtributePasswordInput;
import com.boleia.boleia.entity.application.ChangePasswordInput;
import com.boleia.boleia.entity.application.RecoveryPasswordInput;
import com.boleia.boleia.entity.application.RegisterUserInput;
import com.boleia.boleia.entity.application.auth.SignInInput;

@Component
public class UserInputMapper {
    
    public RegisterUserInput toRegisterUserInput(RegisterUserRequest body) {
        return new RegisterUserInput(body.firstName(), body.lastName(), body.phoneNumber(), body.isDriver());
    }

    public ChangePasswordInput toChangePasswordInput(ChangePasswordRequest body) {
        return new ChangePasswordInput(body.driverId(), body.oldPassword(), body.confirmedPassword());
    }

    public SignInInput toSignInInput(SignInRequest body) {
        return new SignInInput(body.phoneNumber(), body.password());
    }

    public AtributePasswordInput toAtributePasswordInput(AtributePasswordRequest body) {
        return new AtributePasswordInput(body.identificationNumber(), body.password());
    }

    public RecoveryPasswordInput toRecoveryPassworInput(RecoveryPasswordRequest body) {
        return new RecoveryPasswordInput(body.phoneNumber(), body.password());
    }
}

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
}

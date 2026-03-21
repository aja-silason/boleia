package com.boleia.boleia.entity.infra.http;

import org.springframework.stereotype.Component;

import com.boleia.boleia.entity.application.AtributePasswordInput;
import com.boleia.boleia.entity.application.ChangePasswordInput;
import com.boleia.boleia.entity.application.RegisterDriverInput;
import com.boleia.boleia.entity.application.auth.SignInInput;

@Component
public class DriverInputMapper {
    
    public RegisterDriverInput toRegisterDriverInput(RegisterDriverRequest body) {
        return new RegisterDriverInput(body.firstName(), body.lastName(), body.phoneNumber(), body.isDriver(), body.identificationNumber(), body.licenseNumber());
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

}

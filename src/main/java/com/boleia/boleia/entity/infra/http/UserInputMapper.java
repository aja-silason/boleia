package com.boleia.boleia.entity.infra.http;

import org.springframework.stereotype.Component;

import com.boleia.boleia.entity.application.FirebaseTokenMessageInput;
import com.boleia.boleia.entity.application.RegisterUserInput;

@Component
public class UserInputMapper {
    
    public RegisterUserInput toRegisterUserInput(RegisterUserRequest body) {
        return new RegisterUserInput(body.firstName(), body.lastName(), body.phoneNumber(), body.isDriver());
    }

    public FirebaseTokenMessageInput toFirebaseTokenMessageInput(FirebaseTokenMessageRequest body){
        return new FirebaseTokenMessageInput(body.userId(), body.fcm());
    }

}

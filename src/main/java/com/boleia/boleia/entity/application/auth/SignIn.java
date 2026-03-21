package com.boleia.boleia.entity.application.auth;

import org.springframework.stereotype.Service;

import com.boleia.boleia.entity.domain.DriverRepository;
import com.boleia.boleia.entity.domain.Password;
import com.boleia.boleia.entity.domain.PasswordIsWrongError;
import com.boleia.boleia.entity.domain.SignInOutput;
import com.boleia.boleia.entity.domain.UserRepository;
import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignIn {
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;

    public Result<SignInOutput, DomainError> execute(SignInInput input){
        var userOrErr = this.userRepository.findByPhoneNumber(input.phoneNumber());
        if(userOrErr.isError()) return Result.error(userOrErr.unwrapError());

        var driverOrErr = this.driverRepository.findByUserId(userOrErr.unwrap().getId());
        if(driverOrErr.isError()) return Result.error(driverOrErr.unwrapError());

        var aPassword = new Password();
        var passwordMatched = aPassword.matches(input.password(), driverOrErr.unwrap().getPassword());

        if(!passwordMatched.unwrap()) return Result.error(new PasswordIsWrongError());

        var driver = driverOrErr.unwrap();
        var user = userOrErr.unwrap();

        var out = new SignInOutput(
            user.getFirstName(),
            user.getLastName(),
            user.getPhoneNumber(),
            driver.getIdentificationNumber(),
            driver.getLicenseNumber(),
            driver.getStatus().getValue(),
            null,
            null
        );

        return Result.ok(out);
    }
}

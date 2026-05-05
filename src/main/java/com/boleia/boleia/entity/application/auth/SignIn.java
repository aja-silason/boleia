package com.boleia.boleia.entity.application.auth;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.boleia.boleia.entity.domain.DriverRepository;
import com.boleia.boleia.entity.domain.Password;
import com.boleia.boleia.entity.domain.PasswordIsWrongError;
import com.boleia.boleia.entity.domain.SignInOutput;
import com.boleia.boleia.entity.domain.UserIsAlreadyBanedError;
import com.boleia.boleia.entity.domain.UserIsAlreadyDeactivatedError;
import com.boleia.boleia.entity.domain.UserIsAlreadyDeclinedError;
import com.boleia.boleia.entity.domain.UserIsAlreadyPendingError;
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

        var driver = driverOrErr.unwrap();
        var user = userOrErr.unwrap();
        if(user.isBanned()) return Result.error(new UserIsAlreadyBanedError());
        if(user.isDeactivated()) return Result.error(new UserIsAlreadyDeactivatedError());
        if(user.isDeclined()) return Result.error(new UserIsAlreadyDeclinedError());
        if(user.isPending()) return Result.error(new UserIsAlreadyPendingError());

        var aPassword = new Password();
        var passwordMatched = aPassword.matches(input.password(), driver.getPassword());
        if(passwordMatched.isError()) return Result.error(passwordMatched.unwrapError());

        if(!passwordMatched.unwrap()) return Result.error(new PasswordIsWrongError());

        LocalDateTime expirationDate = LocalDateTime.now().plusDays(3);

        String userWillBeSignedUntil = expirationDate.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        var out = new SignInOutput(
            driver.getId().toString(),
            user.getId().toString(),
            user.getFirstName(),
            user.getLastName(),
            user.getPhoneNumber(),
            driver.getIdentificationNumber(),
            driver.getLicenseNumber(),
            driver.getStatus().getValue(),
            userWillBeSignedUntil
        );

        return Result.ok(out);
    }
}

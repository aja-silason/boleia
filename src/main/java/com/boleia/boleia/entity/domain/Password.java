package com.boleia.boleia.entity.domain;

import java.util.regex.Pattern;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;
public class Password {
    
    private static final Pattern ONLY_NUMBERS = Pattern.compile("\\d{6}");
    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    public Result<String, DomainError> fromPlainText(String rawPassword) {
        
        if(rawPassword == null) return Result.error(new PasswordLengthError());

        if(!ONLY_NUMBERS.matcher(rawPassword).matches()) return Result.error(new PasswordMustBeNumbersAndHaveSixDigitsError());

        return Result.ok(ENCODER.encode(rawPassword));
    }

    public Result<Boolean, RawAndPasswordMustProvidedError> matches(String rawPassword, String hashedPassword) {

        if (rawPassword == null || hashedPassword == null) return Result.error(new RawAndPasswordMustProvidedError());

        return Result.ok(ENCODER.matches(rawPassword, hashedPassword));
    }

}

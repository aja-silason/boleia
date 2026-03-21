package com.boleia.boleia.entity.domain;

import java.util.regex.Pattern;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
public class Password {
    
    private static final Pattern ONLY_NUMBERS = Pattern.compile("\\d{6}");
    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    public String fromPlainText(String rawPassword) {
        
        if(rawPassword == null) {
            throw new IllegalArgumentException("Password must be provided");
        }

        if(!ONLY_NUMBERS.matcher(rawPassword).matches()) {
            throw new IllegalArgumentException("Password must have 6 digits");
        }


        return ENCODER.encode(rawPassword);
    }

    public Boolean matches(String rawPassword, String hashedPassword) {

        if (rawPassword == null || hashedPassword == null){
            throw new IllegalArgumentException("Both raw and hash password must be provided");
        }

        return ENCODER.matches(rawPassword, hashedPassword);
    }

}

package com.boleia.boleia.shared.application;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.boleia.boleia.entity.domain.User;
import com.boleia.boleia.shared.error.DomainError;
import com.boleia.boleia.shared.types.Result;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class GenerateToken {
    
    @Value("${jwt.tokenKey}")
    private String SECRET;
    private final long EXPIRATION_TIME = 86400000;

    public Result<String, DomainError> generateToken(User payload) {
        var key = Keys.hmacShaKeyFor(SECRET.getBytes());

        var jwt = Jwts.builder()
                .setId(payload.getId().toString())
                .setPayload(payload.toString())
                .setSubject(payload.getPhoneNumber())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return Result.ok(jwt);
    }
}

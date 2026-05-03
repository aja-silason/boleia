package com.boleia.boleia.support.domain.user;

import java.util.UUID;

import lombok.Getter;

@Getter
public class User {
    private UUID id;
    private String firstName;
    private String lastName;

    public User(
        UUID id,
        String firstName,
        String lastName
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

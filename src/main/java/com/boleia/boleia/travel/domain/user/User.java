package com.boleia.boleia.travel.domain.user;

import java.util.UUID;

import lombok.Getter;

@Getter
public class User {
    private UUID id;
    private EntityType type;

    public User(
        UUID id,
        EntityType type
    ) {
        this.id = id;
        this.type = type;
    }
}

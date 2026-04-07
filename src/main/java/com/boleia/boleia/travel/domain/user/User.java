package com.boleia.boleia.travel.domain.user;

import java.util.UUID;

import lombok.Getter;

@Getter
public class User {
    private UUID id;
    private EntityType type;
    private String fcm;

    public User(
        UUID id,
        EntityType type,
        String fcm
    ) {
        this.id = id;
        this.type = type;
        this.fcm = fcm;
    }
}

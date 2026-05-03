package com.boleia.boleia.support.domain;

import java.util.UUID;

import lombok.Getter;

@Getter
public class ChatSupport {

    private UUID id;
    private UUID userId;
    private String message;

    public ChatSupport(
        UUID id,
        UUID userId,
        String message
    ) {
        this.id = id;
        this.message = message;
        this.userId = userId;
    }

    public static ChatSupport create(
        UUID userId,
        String message
    ){
        return new ChatSupport(
            UUID.randomUUID(), 
            userId, 
            message
        );
    }

    public static ChatSupport from(
        UUID id,
        UUID userId,
        String message
    ) {
        return new ChatSupport(
            id,
            userId,
            message
        );
    }
    
}

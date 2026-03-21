package com.boleia.boleia.travel.domain;

import java.util.UUID;

import com.boleia.boleia.travel.domain.user.EntityType;

import lombok.Getter;

@Getter
public class Rating {
    private UUID id;
    private UUID userId;
    private Integer rating;
    private EntityType entityType;

    private Rating(
        UUID id,
        UUID userId,
        Integer rating,
        EntityType entityType
    ){
        this.id = id;
        this.userId = userId;
        this.rating = rating;
        this.entityType = entityType;
    }

    public static Rating create(
        UUID userId,
        Integer rating,
        EntityType entityType
    ){
        return new Rating(UUID.randomUUID(), userId, rating, entityType);
    }

    public static Rating from(
        UUID id,
        UUID userId,
        Integer rating,
        EntityType entityType
    ){
        return new Rating(id, userId, rating, entityType);
    }

}

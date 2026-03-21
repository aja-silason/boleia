package com.boleia.boleia.entity.domain;

import java.util.UUID;

import lombok.Getter;

@Getter
public class User {
    private UUID id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private EntityType type;
    private EntityStatus status;

    public User(
        UUID id,
        String firstName,
        String lastName,
        String phoneNumber,
        EntityType type,
        EntityStatus status
    ){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.status = status;
    }

    public static User create(
        String firstName,
        String lastName,
        String phoneNumber,
        EntityType type
    ){
        return new User(
            UUID.randomUUID(),
            firstName,
            lastName,
            phoneNumber,
            type,
            EntityStatus.PENDING
        );
    }

    public static User from(
        UUID id,
        String firstName,
        String lastName,
        String phoneNumber,
        EntityType type,
        EntityStatus status
    ){
        return new User(
            id,
            firstName,
            lastName,
            phoneNumber,
            type,
            status
        );
    }


    public void approve(){
        this.status = EntityStatus.APPROVED;
    }

    public boolean isApproved(){
        return this.status == EntityStatus.APPROVED;
    }

    public void ban(){
        this.status = EntityStatus.BANED;
    }

    public boolean isBanned(){
        return this.status == EntityStatus.BANED;
    }

    public void decline(){
        this.status = EntityStatus.DECLINED;
    }

    public boolean isDeclined(){
        return this.status == EntityStatus.DECLINED;
    }


}

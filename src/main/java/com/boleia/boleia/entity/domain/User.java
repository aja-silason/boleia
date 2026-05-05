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
    private String fcm;

    public User(
        UUID id,
        String firstName,
        String lastName,
        String phoneNumber,
        EntityType type,
        EntityStatus status,
        String fcm
    ){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.status = status;
        this.fcm = fcm;
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
            EntityStatus.PENDING,
            null
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
            status,
            null
        );
    }

    public void addFCM(String fcm){
        this.fcm = fcm;
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

    public void deactive(){
        this.status = EntityStatus.DEACTIVATED;
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

    public boolean isDeactivated(){
        return this.status == EntityStatus.DEACTIVATED;
    }

    public boolean isPending(){
        return this.status == EntityStatus.PENDING;
    }


}

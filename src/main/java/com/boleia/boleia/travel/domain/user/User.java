package com.boleia.boleia.travel.domain.user;

import java.util.UUID;

import lombok.Getter;

@Getter
public class User {
    private UUID id;
    private EntityType type;
    private String fcm;
    private EntityStatus status;

    public User(
        UUID id,
        EntityType type,
        String fcm,
        EntityStatus status
    ) {
        this.id = id;
        this.type = type;
        this.fcm = fcm;
        this.status = status;
    }
    
    public boolean isApproved(){
        return this.status == EntityStatus.APPROVED;
    }

    public boolean isBanned(){
        return this.status == EntityStatus.BANED;
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

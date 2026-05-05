package com.boleia.boleia.travel.domain.driver;

import java.util.UUID;

import lombok.Getter;

@Getter
public class Driver {
    private UUID id;
    private String fcm;
    private EntityStatus status;

    public Driver(
        UUID id,
        String fcm,
        EntityStatus status
    ) {
        this.id = id;
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

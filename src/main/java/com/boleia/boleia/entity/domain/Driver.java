package com.boleia.boleia.entity.domain;

import java.util.UUID;

import lombok.Getter;

@Getter
public class Driver {
    private UUID id;
    private String identificationNumber;
    private String licenseNumber;
    private String password;
    private EntityStatus status;
    private User user;

    public Driver(
        UUID id,
        String identificationNumber,
        String licenseNumber,
        String password,
        EntityStatus status,
        User user
    ){
        this.id = id;
        this.identificationNumber = identificationNumber;
        this.licenseNumber = licenseNumber;
        this.password = password;
        this.status = status;
        this.user = user;
    }

    public static Driver create(
        String identificationNumber,
        String licenseNumber,
        User user
    ){
        return new Driver(
            UUID.randomUUID(),
            identificationNumber,
            licenseNumber,
            null,
            EntityStatus.PENDING,
            user    
        );
    }

    public static Driver from(
        UUID id,
        String identificationNumber,
        String licenseNumber,
        String password,
        EntityStatus status,
        User user
    ){
        return new Driver(
            id,
            identificationNumber,
            licenseNumber,
            password,
            status,
            user
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

    public void changeIdentificationNumber(String newIdentificationNumber){
        this.identificationNumber = newIdentificationNumber;
    }

    public void changeLicenseNumber(String newLicenseNumber){
        this.licenseNumber = newLicenseNumber;
    }

    public void changePassword(String newPassword){
        this.password = newPassword;
    }

}

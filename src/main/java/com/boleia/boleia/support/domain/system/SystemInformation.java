package com.boleia.boleia.support.domain.system;

import java.util.UUID;

import lombok.Getter;

@Getter
public class SystemInformation {
    private UUID id;
    private String aplicationVersion;
    private String centralPhoneNumber;

    private SystemInformation(
        UUID id,
        String aplicationVersion,
        String centralPhoneNumber
    ){
        this.id = id;
        this.aplicationVersion = aplicationVersion;
        this.centralPhoneNumber = centralPhoneNumber;
    }

    public static SystemInformation create(
        String aplicationVersion,
        String centralPhoneNumber
    ){
        return new SystemInformation(
            UUID.randomUUID(),
            aplicationVersion,
            centralPhoneNumber
        );
    }

    public static SystemInformation from(
        UUID id,
        String aplicationVersion,
        String centralPhoneNumber
    ) {
        return new SystemInformation(
            id, 
            aplicationVersion, 
            centralPhoneNumber
        );
    }

    public void newApplicationVersion(String version) {
        this.aplicationVersion = version;
    }

    public void addCentralPhone(String phone) {
        this.centralPhoneNumber= phone;
    }

}

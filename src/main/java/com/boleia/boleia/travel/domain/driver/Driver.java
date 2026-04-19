package com.boleia.boleia.travel.domain.driver;

import java.util.UUID;

import lombok.Getter;

@Getter
public class Driver {
    private UUID id;
    private String fcm;

    public Driver(
        UUID id,
        String fcm
    ) {
        this.id = id;
        this.fcm = fcm;
    }

}

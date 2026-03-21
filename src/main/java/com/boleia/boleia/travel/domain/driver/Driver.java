package com.boleia.boleia.travel.domain.driver;

import java.util.UUID;

import lombok.Getter;

@Getter
public class Driver {
    private UUID id;

    public Driver(
        UUID id
    ) {
        this.id = id;
    }

}

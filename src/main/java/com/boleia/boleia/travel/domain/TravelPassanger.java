package com.boleia.boleia.travel.domain;

import java.util.UUID;

import lombok.Getter;

@Getter
public class TravelPassanger {
    private final UUID passangerId;
    private TravelPassangerStatus status;

    private TravelPassanger(
        UUID passangerId,
        TravelPassangerStatus status
    ){
        this.passangerId = passangerId;
        this.status = status;
    }

    public static TravelPassanger create(
        UUID passangerId
    ){
        return new TravelPassanger(
            passangerId,
            TravelPassangerStatus.PENDING
        );
    }


    public static TravelPassanger from(
        UUID passangerId,
        TravelPassangerStatus status
    ){
        return new TravelPassanger(
            passangerId,
            status
        );
    }

    public void accept(){
        this.status = TravelPassangerStatus.ACCEPTED;
    }

    public void refused(){
        this.status = TravelPassangerStatus.REFUSED;
    }
}

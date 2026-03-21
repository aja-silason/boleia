package com.boleia.boleia.travel.domain;

import lombok.Getter;

@Getter
public enum TravelPassangerStatus {
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    REFUSED("REFUSED");

    private final String value;

    TravelPassangerStatus(String value) {
        this.value = value;
    }

    public static TravelPassangerStatus fromValue(String value) {
    for (TravelPassangerStatus type : TravelPassangerStatus.values()) {
      if (type.value.equalsIgnoreCase(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Invalid DriverStatus: " + value);
  }
}

package com.boleia.boleia.travel.domain;

import lombok.Getter;

@Getter
public enum TravelStatus {
    OPEN("OPEN"),
    FUEL("FUEL"),
    STARTED("STARTED"),
    COMPLETED("COMPLETED");

    private final String value;

    TravelStatus(String value) {
        this.value = value;
    }

    public static TravelStatus fromValue(String value) {
    for (TravelStatus type : TravelStatus.values()) {
      if (type.value.equalsIgnoreCase(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Invalid DriverStatus: " + value);
  }
}

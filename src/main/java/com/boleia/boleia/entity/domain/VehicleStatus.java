package com.boleia.boleia.entity.domain;

import lombok.Getter;

@Getter
public enum VehicleStatus {
    PENDING("PENDING"),
    AVAILABLE("AVAILABLE"),
    NOTAVAILABLE("NOTAVAILABLE"),
    SUSPENDED("SUSPENDED");

    private final String value;

    VehicleStatus(String value) {
        this.value = value;
    }

    public static VehicleStatus fromValue(String value) {
    for (VehicleStatus type : VehicleStatus.values()) {
      if (type.value.equalsIgnoreCase(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Invalid DriverStatus: " + value);
  }
}

package com.boleia.boleia.entity.domain;

import lombok.Getter;

@Getter
public enum EntityStatus {
    APPROVED("APPROVED"),
    DECLINED("DECLINED"),
    PENDING("PENDING"),
    BANED("BANED");

    private final String value;

    EntityStatus(String value) {
        this.value = value;
    }

    public static EntityStatus fromValue(String value) {
    for (EntityStatus type : EntityStatus.values()) {
      if (type.value.equalsIgnoreCase(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Invalid DriverStatus: " + value);
  }
}

package com.boleia.boleia.travel.domain.user;

import lombok.Getter;

@Getter
public enum EntityType {
    DRIVER("DRIVER"),
    PASSANGER("PASSANGER");

    private final String value;

    EntityType(String value) {
        this.value = value;
    }

    public static EntityType fromValue(String value) {
    for (EntityType type : EntityType.values()) {
      if (type.value.equalsIgnoreCase(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Invalid EntityType: " + value);
  }
}

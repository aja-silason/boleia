package com.boleia.boleia.support.domain.politicsAndPrivacy;

import java.util.UUID;

import lombok.Getter;

@Getter
public class Politics {
    private UUID id;
    private String title;
    private String description;
    private Integer order;

    private Politics(
        UUID id,
        String title,
        String description,
        Integer order
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.order = order;
    }

    public static Politics create(
        String title,
        String description,
        Integer order
    ) {
        return new Politics(UUID.randomUUID(), title, description, order);
    }

    public static Politics from(
        UUID id,
        String title,
        String description,
        Integer order
    ) {
        return new Politics(id, title, description, order);
    }

}

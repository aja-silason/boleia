package com.boleia.boleia.support.domain.politicsAndTerms;

import java.util.UUID;

import lombok.Getter;

@Getter
public class Terms {
    private UUID id;
    private String title;
    private String description;
    private Integer order;

    private Terms(
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

    public static Terms create(
        String title,
        String description,
        Integer order
    ) {
        return new Terms(UUID.randomUUID(), title, description, order);
    }

    public static Terms from(
        UUID id,
        String title,
        String description,
        Integer order
    ) {
        return new Terms(id, title, description, order);
    }

}

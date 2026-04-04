package com.boleia.boleia.shared.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

public class TravelQueryBuilder {
    private String location;
    private Integer minSeats;

    public TravelQueryBuilder withLocation(String location) {
        this.location = location;
        return this;
    }

    public TravelQueryBuilder withAvailableSeats(Integer minSeats) {
        this.minSeats = minSeats;
        return this;
    }

    public Specification<TravelModel> build() {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (location != null && !location.isBlank()) {
                String searchLower = location.toLowerCase();

                Predicate originPredicate = cb.equal(
                    cb.lower(root.get("origin")), 
                    searchLower
                );
                
                Predicate destinyPredicate = cb.equal(
                    cb.lower(root.get("destiny")), 
                    searchLower
                );

                predicates.add(cb.or(originPredicate, destinyPredicate));
            }

            if (minSeats != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("seats"), minSeats));
            }

            predicates.add(cb.equal(root.get("status"), "OPEN"));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

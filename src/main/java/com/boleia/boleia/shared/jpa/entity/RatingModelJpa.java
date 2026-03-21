package com.boleia.boleia.shared.jpa.entity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RatingModelJpa extends JpaRepository<RatingModel, String>, JpaSpecificationExecutor<RatingModel> {
    Optional<RatingModel> findByUserId(String id);
}

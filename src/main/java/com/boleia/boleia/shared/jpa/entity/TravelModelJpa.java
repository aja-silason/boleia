package com.boleia.boleia.shared.jpa.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelModelJpa extends JpaRepository<TravelModel, String>, JpaSpecificationExecutor<TravelModel> {
    List<TravelModel> findAllByDriverId(String id);
}

package com.boleia.boleia.shared.jpa.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelModelJpa extends JpaRepository<TravelModel, String>, JpaSpecificationExecutor<TravelModel> {
    List<TravelModel> findAllByDriverId(String id);

    @Query(nativeQuery = true, value = 
        "SELECT t.* FROM travels t " +
        "JOIN travel_passanger tp ON t.id = tp.travel_id " +
        "WHERE tp.passenger_id = :passengerId AND tp.status = 'APPROVED'")
    List<TravelModel> findAllByPassengers(@Param("passengerId") String passengerId);
}

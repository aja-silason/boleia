package com.boleia.boleia.shared.jpa.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PoliticsModelJpa extends JpaRepository<PoliticsModel, String>, JpaSpecificationExecutor<PoliticsModel> {}

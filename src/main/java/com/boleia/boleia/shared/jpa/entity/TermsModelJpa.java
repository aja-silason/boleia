package com.boleia.boleia.shared.jpa.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TermsModelJpa extends JpaRepository<TermsModel, String>, JpaSpecificationExecutor<TermsModel> {}

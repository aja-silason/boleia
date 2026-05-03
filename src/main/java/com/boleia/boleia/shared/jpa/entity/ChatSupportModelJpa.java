package com.boleia.boleia.shared.jpa.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChatSupportModelJpa extends JpaRepository<ChatSupportModel, String>, JpaSpecificationExecutor<ChatSupportModel> {}

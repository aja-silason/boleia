package com.boleia.boleia.shared.jpa.entity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserModelJpa extends JpaRepository<UserModel, String>, JpaSpecificationExecutor<UserModel> {
    Optional<UserModel> findByPhoneNumber(String phoneNumber);
}

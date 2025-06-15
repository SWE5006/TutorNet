package com.project.tutornet.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tutornet.entity.UserInfoEntity;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, UUID> {
    Optional<UserInfoEntity> findByEmailAddress(String emailAddress);
    Optional<UserInfoEntity> findByUsername(String username);
    boolean existsByEmailAddress(String emailAddress);
    boolean existsByUsername(String username);
} 
package com.project.tutornet.repository;

import com.project.tutornet.entity.RefreshTokenEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepo  extends CrudRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByRefreshToken(String refreshToken);

    List<RefreshTokenEntity> findAllByUserId(UUID userId);
}

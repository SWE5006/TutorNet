package com.project.tutornet.repository;

import com.project.tutornet.entity.UserInfoEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository
  extends ListCrudRepository<UserInfoEntity, UUID> {
  Optional<UserInfoEntity> findByEmailAddress(String email);

  List<UserInfoEntity> findAll();

  @Modifying
  @Transactional
  @Query(
    "UPDATE UserInfoEntity u " +
    "SET u.username = :username, " +
    "u.mobileNumber = :mobileNumber, " +
    "u.roles = :roles, " +
    "u.activeStatus = :activeStatus " +
    "WHERE u.id = :userId"
  )
  Integer updateUser(
    @Param("userId") UUID userId,
    @Param("username") String username,
    @Param("mobileNumber") String mobileNumber,
    @Param("roles") String roles,
    @Param("activeStatus") String activeStatus
  );
}

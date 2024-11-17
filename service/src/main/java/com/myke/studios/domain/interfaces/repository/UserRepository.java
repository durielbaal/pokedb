package com.myke.studios.domain.interfaces.repository;

import com.myke.studios.domain.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;


/**
 * User Repository.
 */
public interface UserRepository extends R2dbcRepository<UserEntity, UUID> {

  /**
   * Searching by usermane in User table.
   * @param username .
   * @return .
   */
  @Query("SELECT u FROM Users u WHERE u.username = :username")
  Mono<UserEntity> findByUsername(String username);
}

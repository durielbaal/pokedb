package com.myke.studios.domain.interfaces.repository;

import com.myke.studios.domain.entity.UserRoleEntity;
import java.util.UUID;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * User role repository.
 */
@Repository
public interface UserRoleRepository extends R2dbcRepository<UserRoleEntity, UUID> {

  /**
   * Searching roles of username.
   * @param username .
   * @return .
   */
  @Query("SELECT * FROM User_roles WHERE username = :username")
  public Flux<UserRoleEntity> getRolesByUsername(String username);


}

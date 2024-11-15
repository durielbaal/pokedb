package com.myke.studios.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Secondary table with user and his/her role.
 */
@Table("user_roles")
@Data
public class UserRoleEntity {

  /**
   * Id of username and relation with role.
   */
  @Id
  private Long id;
  /**
   * Username.
   */
  private String username;
  /**
   * Role.
   */
  private String role;

}

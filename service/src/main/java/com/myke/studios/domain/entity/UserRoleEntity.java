package com.myke.studios.domain.entity;

import com.myke.studios.enums.Role;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Secondary table with user and his/her role.
 */
@Table("User_roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleEntity {

  /**
   * Id of username and relation with role.
   */
  @Id
  private UUID id;
  /**
   * Username.
   */
  private String username;
  /**
   * Role.
   */
  private Role role;

}

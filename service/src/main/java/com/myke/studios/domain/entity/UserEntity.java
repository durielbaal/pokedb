package com.myke.studios.domain.entity;

import com.myke.studios.dto.UserDto;
import com.myke.studios.enums.Role;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * User entity (table in DB).
 */
@Table("User")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity  {

  /**
   * id generado por user en postgre.
   */
  @Id
  private UUID id;
  /**
   * Username.
   */
  private String username;
  /**
   * Password.
   */
  private String password;

  /**
   * Convert UserEntity to Dto.
   * @param roleFlux flux of Roles, to manage in this object (dto).
   * @return .
   */
  public Mono<UserDto> fromEntityToDto(Flux<Role> roleFlux) {
    return roleFlux.map(Role::getRoleName)
        .collectList()
        .map(roleNames -> new UserDto(this.username, this.getPassword(), roleNames));
  }

}
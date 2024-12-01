package com.myke.studios.domain.entity;

import com.myke.studios.abstracts.AbstractUserEvent;
import com.myke.studios.dto.UserDto;
import com.myke.studios.enums.Role;
import com.myke.studios.userevent.login.UserLoginEvent;
import com.myke.studios.userevent.register.UserRegisterEvent;
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
@Table("Users")
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

  /**
   * From Dto to Entity.
   * @param abstractUserEvent .
   * @return Userentity itself.
   */
  public static UserEntity fromDtoToEntity(AbstractUserEvent abstractUserEvent) {
    UserEntity userEntity = new UserEntity();
    if (abstractUserEvent instanceof UserLoginEvent) {
      UserLoginEvent userLoginEvent = (UserLoginEvent) abstractUserEvent;
      userEntity.setUsername(userLoginEvent.getBody().getUsername());
      userEntity.setPassword(userLoginEvent.getBody().getPassword());
    } else if (abstractUserEvent instanceof UserRegisterEvent) {
      UserRegisterEvent userRegisterEvent = (UserRegisterEvent) abstractUserEvent;
      userEntity.setUsername(userRegisterEvent.getBody().getUsername());
      userEntity.setPassword(userRegisterEvent.getBody().getPassword());
    }
    return userEntity;
  }
}
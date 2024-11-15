package com.myke.studios.domain.entity;

import com.myke.studios.dto.UserDto;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * User entity (table in DB).
 */
@Table("User")
@Data
public class UserEntity implements UserDetails {

  /**
   * Username.
   */
  @Id
  private String username;
  /**
   * Password.
   */
  private String password;
  /**
   * List of roles.
   */
  private List<String> roles;

  /**
   * Converter from Entity to Dto.
   * @return UserDto.
   */
  public UserDto fromEntityToDto() {
    return new UserDto(this.username,this.getPassword(),this.roles);
  }

  /**
   * Get SimpleGrantedAuthority from role String list.
   * @return SimpleGrantedAuthority list.
   */
  @Override
  public List<SimpleGrantedAuthority> getAuthorities() {
    return roles.stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
        .toList();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
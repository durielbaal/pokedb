package com.myke.studios.application.service;

import com.myke.studios.config.AnyUserAuthConfig;
import com.myke.studios.domain.entity.UserEntity;
import com.myke.studios.domain.entity.UserRoleEntity;
import com.myke.studios.domain.input.UserInputPort;
import com.myke.studios.domain.interfaces.repository.UserRepository;
import com.myke.studios.domain.interfaces.repository.UserRoleRepository;
import com.myke.studios.enums.Role;
import com.myke.studios.jwt.JwtService;
import io.netty.channel.VoidChannelPromise;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.UserDatabase;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * User service.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserInputPort {

  /**
   * repository of user management.
   */
  private final UserRepository userRepository;
  /**
   * User Role repository management.
   */
  private  final UserRoleRepository userRoleRepository;
  /**
   * Password encoder.
   */
  private final PasswordEncoder passwordEncoder;
  /**
   * User authenticator.
   */
  private  final AnyUserAuthConfig anyUserAuthConfig;
  /**
   * JWT management service.
   */
  private final JwtService jwtService;
  /**
   * Redis template.
   */
  private RedisTemplate<String, String> redisTemplate;

  /**
   * User login.
   * @param userEntityRequested user itself.
   * @return Response.
   */
  public Mono<ResponseEntity<Map<String, String>>> login(UserEntity userEntityRequested) {
    return getUserByUsername(userEntityRequested.getUsername())
        .flatMap(userDataBase -> {
          Flux<Role> roleFlux = getRolesByUsername(userDataBase);
          return userDataBase.fromEntityToDto(roleFlux)
              .flatMap(userDto -> {
                anyUserAuthConfig.setUserDto(userDto);
                Authentication authentication = anyUserAuthConfig.authenticate(
                    new UsernamePasswordAuthenticationToken(
                        userEntityRequested.getUsername(),
                        userEntityRequested.getPassword()
                    )
                );
                String token = jwtService.generateToken(authentication);
                ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
                valueOps.set(userDto.getUsername(), token, 1, TimeUnit.DAYS);
                Map<String, String> response = new HashMap<>();
                response.put("token", token);

                return Mono.just(ResponseEntity.ok(response));
              });
        });
  }

  /**
   * Log out of the application.
   * @param token .
   * @return .
   */
  public Mono<Void> logout(String token) {
    if (token == null || !token.startsWith("Bearer ")) {
      return Mono.error(new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "Authorization header is invalid"));
    }
    token = token.substring(7);
    String username = jwtService.getUsernameFromToken(token);

    return Mono.fromRunnable(() -> redisTemplate.delete(username));
  }

  /**
   * User register.
   * @param userEntity user itself.
   * @return response.
   */
  public Mono<ResponseEntity<String>> register(UserEntity userEntity) {
    return this.registerUser(userEntity)
        .map(user -> ResponseEntity.ok("User registered successfully"))
        .onErrorResume(e -> Mono.just(ResponseEntity
            .badRequest()
            .body("Error registering user: " + e.getMessage())));
  }

  /**
   * Get User searching by username.
   * @param username .
   * @return user itself.
   */
  private Mono<UserEntity> getUserByUsername(String username) {
    return userRepository.findByUsername(username)
        .switchIfEmpty(Mono.error(new UsernameNotFoundException(
            "User not found with username: " + username)));
  }

  /**
   * get Roles searching by user.
   * @param userEntity .
   * @return FLux of roles.
   */
  private Flux<Role> getRolesByUsername(UserEntity userEntity) {
    return userRoleRepository.getRolesByUsername(userEntity.getUsername())
        .map(UserRoleEntity::getRol)
        .switchIfEmpty(Flux.error(new UsernameNotFoundException(
            "No roles found for user: " + userEntity.getUsername())));
  }

  /**
   * Register user into Data base.
   * @param userEntity user itself.
   * @return Mono UserEntity.
   */
  private Mono<UserEntity> registerUser(UserEntity userEntity) {
    if (userEntity == null) {
      return Mono.error(new IllegalArgumentException("Invalid User."));
    }
    if (userEntity.getUsername() == null || userEntity.getUsername().isEmpty()) {
      return Mono.error(new IllegalArgumentException("Invalid Username. Can't be empty"));
    }
    if (userEntity.getPassword() == null || userEntity.getPassword().isEmpty()) {
      return Mono.error(new IllegalArgumentException("Invalid password. Can't be empty"));
    }
    return getUserByUsername(userEntity.getUsername())
        .flatMap(existingUser -> {
          // Si el usuario existe, lanzar error
          return Mono.<UserEntity>error(new IllegalArgumentException("The user already exists."));
        })
        .onErrorResume(UsernameNotFoundException.class, e -> {
          userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
          UserRoleEntity userRole = new UserRoleEntity();
          userRole.setRol(Role.USER);
          userRole.setUsername(userEntity.getUsername());
          return userRoleRepository.save(userRole)
              .then(userRepository.save(userEntity));
        });
  }




}

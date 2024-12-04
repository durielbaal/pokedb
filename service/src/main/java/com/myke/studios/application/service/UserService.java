package com.myke.studios.application.service;

import com.myke.studios.config.AnyUserAuthConfig;
import com.myke.studios.constant.ConstantEvent;
import com.myke.studios.domain.entity.UserEntity;
import com.myke.studios.domain.entity.UserRoleEntity;
import com.myke.studios.domain.input.UserInputPort;
import com.myke.studios.domain.interfaces.repository.UserLoginEventRepository;
import com.myke.studios.domain.interfaces.repository.UserRegisterEventRepository;
import com.myke.studios.domain.interfaces.repository.UserRepository;
import com.myke.studios.domain.interfaces.repository.UserRoleRepository;
import com.myke.studios.enums.Role;
import com.myke.studios.jwt.JwtService;
import com.myke.studios.userevent.login.UserLoginEvent;
import com.myke.studios.userevent.register.UserRegisterEvent;
import io.jsonwebtoken.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
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
   * User Event Register Repository.
   */
  private final UserRegisterEventRepository userRegisterEventRepository;
  /**
   * User Event Login Repository.
   */
  private final UserLoginEventRepository userLoginEventRepository;
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
  private final RedisTemplate<String, String> redisTemplate;

  /**
   * User login.
   * @param userLoginEvent user itself.
   * @return Response.
   */
  public Mono<ResponseEntity<String>> login(UserLoginEvent userLoginEvent) {
    UserEntity userEntityRequested = UserEntity.fromDtoToEntity(userLoginEvent);
    return getUserByUsername(userEntityRequested.getUsername())
        .flatMap(userDataBase -> {
          Flux<Role> roleFlux = getRolesByUsername(userDataBase);
          return userDataBase.fromEntityToDto(roleFlux)
              .flatMap(userDto -> {
                anyUserAuthConfig.setUserDto(userDto);
                try {
                  Authentication authentication = anyUserAuthConfig.authenticate(
                      new UsernamePasswordAuthenticationToken(
                          userEntityRequested.getUsername(),
                          userEntityRequested.getPassword()
                      )
                  );
                  String token = jwtService.generateToken(authentication);
                  ValueOperations<String, String> valueOps = redisTemplate.opsForValue();
                  valueOps.set(userDto.getUsername(), token, 1, TimeUnit.DAYS);
                  userLoginEvent.getHeader().setResponse("Login success...");
                  userLoginEventRepository.save(userLoginEvent);
                  log.info("token:".concat(token));
                  return Mono.just(ResponseEntity.ok("token:".concat(token)));
                } catch (BadCredentialsException | InvalidKeyException e) {
                  userLoginEvent.getHeader().setResponse("Error".concat(e.getMessage()));
                  userLoginEventRepository.save(userLoginEvent);
                  log.error("Error:".concat(e.getMessage()));
                  return  Mono.just(ResponseEntity.badRequest().body("bad credentials..."));
                }
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
   * @param userRegisterEvent user itself.
   * @return response.
   */
  public Mono<ResponseEntity<String>> register(UserRegisterEvent userRegisterEvent) {
    return this.registerUser(userRegisterEvent)
        .flatMap(user -> {
          userRegisterEvent.getHeader()
              .setResponse("User registered successfully");
          userRegisterEventRepository.save(userRegisterEvent);
          log.info("User registered successfully");
          return Mono.just(ResponseEntity.ok("User registered successfully"));
        })
        .onErrorResume(e -> {
          userRegisterEvent.getHeader()
              .setResponse("Register error with this user".concat(e.getMessage()));
          userRegisterEventRepository.save(userRegisterEvent);
          log.error("Register error with this user".concat(e.getMessage()));
          return Mono.just(ResponseEntity.badRequest()
              .body("Register error with this user".concat(e.getMessage())));
        });
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
    return userRoleRepository
        .getRolesByUsername(userEntity.getUsername())
        .map(UserRoleEntity::getRole)
        .switchIfEmpty(Flux.error(new UsernameNotFoundException(
            "No roles found for user: " + userEntity.getUsername())));
  }

  /**
   * Register user into Data base.
   * @param userRegisterEvent user and event itself.
   * @return Mono UserEntity.
   */
  private Mono<UserEntity> registerUser(UserRegisterEvent userRegisterEvent) {
    UserEntity userEntity = UserEntity.fromDtoToEntity(userRegisterEvent);
    return getUserByUsername(userEntity.getUsername())
        .flatMap(existingUser -> {
          return Mono.<UserEntity>error(new IllegalArgumentException("The user already exists."));
        })
        .onErrorResume(UsernameNotFoundException.class, e -> {
          userEntity.setPassword(userEntity.getPassword());
          UserRoleEntity userRole = new UserRoleEntity();
          userRole.setRole(Role.USER);
          userRole.setUsername(userEntity.getUsername());
          return userRoleRepository.save(userRole)
              .then(userRepository.save(userEntity));
        });
  }
}

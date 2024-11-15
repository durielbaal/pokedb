package com.myke.studios.application.service;

import com.myke.studios.domain.entity.UserEntity;
import com.myke.studios.domain.interfaces.repository.UserRepository;
import com.myke.studios.domain.output.UserOutputPort;
import com.myke.studios.jwt.JwtService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

/**
 * User service.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserOutputPort {

  /**
   * repository of user management.
   */
  private final UserRepository userRepository;
  /**
   * Password encoder.
   */
  private final PasswordEncoder passwordEncoder;
  /**
   * User authenticator.
   */
  /**
   * JWT management service.
   */
  private final JwtService jwtService;

  /**
   * Register user into Data base.
   * @param userEntity user itself.
   * @return nothing.
   */
  private Mono<UserEntity> registerUser(UserEntity userEntity) {
    userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
    return userRepository.save(userEntity);
  }

  /**
   * User login.
   * @param userEntity user itself.
   * @return Response.
   */
  public ResponseEntity<Map<String, String>> login(UserEntity userEntity) {
    String token = "";//jwtService.generateToken(authentication);
    Map<String, String> response = new HashMap<>();
    response.put("token", token);
    return ResponseEntity.ok(response);
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



}

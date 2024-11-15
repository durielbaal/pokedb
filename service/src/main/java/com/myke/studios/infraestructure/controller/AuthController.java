package com.myke.studios.infraestructure.controller;

import com.myke.studios.domain.entity.UserEntity;
import com.myke.studios.domain.output.UserOutputPort;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Authentication controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  /**
   * Contract between controller and service.
   */
  public final UserOutputPort userOutputPort;

  /**
   * user register.
   * @param userEntity .
   * @return response.
   */
  @PostMapping("/register")
  public Mono<ResponseEntity<String>> register(@RequestBody UserEntity userEntity) {
    return userOutputPort.register(userEntity);
  }
}

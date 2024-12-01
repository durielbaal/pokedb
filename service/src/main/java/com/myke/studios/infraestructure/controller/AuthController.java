package com.myke.studios.infraestructure.controller;

import com.myke.studios.constant.ConstantEvent;
import com.myke.studios.domain.entity.UserEntity;
import com.myke.studios.domain.input.UserInputPort;
import com.myke.studios.shared.Constants;
import com.myke.studios.userevent.login.UserLoginEvent;
import com.myke.studios.userevent.register.UserRegisterEvent;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


/**
 * Authentication controller.
 */
@RestController
@RequiredArgsConstructor
public class AuthController {

  /**
   * Contract between controller and service.
   */
  public final UserInputPort userInputPort;

  /**
   * user register.
   * @param userRegisterEvent .
   * @return response.
   */
  @KafkaListener(topics = ConstantEvent.USER_REGISTER_EVENT,
      groupId = ConstantEvent.USER_REGISTER_GROUP)
  public Mono<ResponseEntity<String>> consume(UserRegisterEvent userRegisterEvent) {
    return userInputPort.register(userRegisterEvent);
  }

  /**
   * user register.
   * @param userLoginEvent .
   * @return response.
   */
  @KafkaListener(topics = ConstantEvent.USER_LOGIN_EVENT,
      groupId = ConstantEvent.USER_LOGIN_GROUP)
  public Mono<ResponseEntity<Map<String, String>>> consume(
      @RequestBody UserLoginEvent userLoginEvent) {
    return userInputPort.login(userLoginEvent);
  }

  /**
   * Log out of the application.
   * @param token .
   * @return .
   */
  @PostMapping(path = Constants.USER_LOGOUT)
  public Mono<ResponseEntity<String>> logout(@RequestHeader(value = "Authorization") String token) {
    return userInputPort.logout(token)
        .then(Mono.just(ResponseEntity.ok("Logout successful")))
        .onErrorResume(e ->
          Mono.just(ResponseEntity.status(500)
              .body("There was a problem with the logout process: " + e.getMessage()))
        );
  }

  /**
   * Admin dashboard.
   * @return welcome.
   */
  @GetMapping(path = Constants.ADMIN_DASHBOARD)
  @PreAuthorize("hasAuthority('admin')")
  public Mono<ResponseEntity<String>> dashboard_admin() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    System.out.println("Authorities: " + authentication.getAuthorities());
    return Mono.just(ResponseEntity.ok("Welcome:this is Admin department:"));
  }

}

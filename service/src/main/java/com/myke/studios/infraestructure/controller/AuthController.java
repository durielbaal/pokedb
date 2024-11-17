package com.myke.studios.infraestructure.controller;

import com.myke.studios.domain.entity.UserEntity;
import com.myke.studios.domain.input.UserInputPort;
import com.myke.studios.shared.Constants;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
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
@RequestMapping(Constants.USER_BASE_PATH)
public class AuthController {

  /**
   * Contract between controller and service.
   */
  public final UserInputPort userInputPort;

  /**
   * user register.
   * @param userEntity .
   * @return response.
   */
  @PostMapping(path = Constants.USER_REGISTER)
  public Mono<ResponseEntity<String>> register(@RequestBody UserEntity userEntity) {
    return userInputPort.register(userEntity);
  }

  /**
   * user register.
   * @param userEntity .
   * @return response.
   */
  @PostMapping(path = Constants.USER_LOGIN)
  public Mono<ResponseEntity<Map<String, String>>> login(@RequestBody UserEntity userEntity) {
    return userInputPort.login(userEntity);
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


}

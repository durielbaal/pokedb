package com.myke.studios.domain.input;

import com.myke.studios.domain.entity.UserEntity;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

/**
 * User input port.
 */
public interface UserInputPort {

  /**
   * User login.
   * @param userEntity user object.
   * @return response.
   */
  Mono<ResponseEntity<Map<String, String>>> login(UserEntity userEntity);

  /**
   * User register.
   * @param userEntity user object.
   * @return response.
   */
  Mono<ResponseEntity<String>> register(UserEntity userEntity);

  /**
   * Log out User.
   * @param token .
   * @return .
   */
  Mono<Void> logout(String token);
}

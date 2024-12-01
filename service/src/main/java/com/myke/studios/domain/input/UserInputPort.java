package com.myke.studios.domain.input;

import com.myke.studios.domain.entity.UserEntity;
import com.myke.studios.userevent.login.UserLoginEvent;
import com.myke.studios.userevent.register.UserRegisterEvent;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

/**
 * User input port.
 */
public interface UserInputPort {

  /**
   * User login.
   * @param userLoginEvent user with events data.
   * @return response.
   */
  Mono<ResponseEntity<Map<String, String>>> login(UserLoginEvent userLoginEvent);

  /**
   * User register.
   * @param userRegisterEvent  user object.
   * @return response.
   */
  Mono<ResponseEntity<String>> register(UserRegisterEvent userRegisterEvent);

  /**
   * Log out User.
   * @param token .
   * @return .
   */
  Mono<Void> logout(String token);
}

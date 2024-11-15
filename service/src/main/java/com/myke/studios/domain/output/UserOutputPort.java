package com.myke.studios.domain.output;

import com.myke.studios.domain.entity.UserEntity;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

/**
 * Contract of UserService and its controller.
 */
public interface UserOutputPort {

  /**
   * User login.
   * @param userEntity user object.
   * @return response.
   */
  ResponseEntity<Map<String, String>> login(UserEntity userEntity);

  /**
   * User register.
   * @param userEntity user object.
   * @return response.
   */
  Mono<ResponseEntity<String>> register(UserEntity userEntity);
}

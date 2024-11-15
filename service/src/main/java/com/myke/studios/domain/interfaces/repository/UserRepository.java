package com.myke.studios.domain.interfaces.repository;

import com.myke.studios.domain.entity.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;


/**
 * User Repository.
 */
public interface UserRepository extends R2dbcRepository<UserEntity, String> {

}

package com.myke.studios.domain.interfaces.repository;

import com.myke.studios.userevent.login.UserLoginEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * User login event repository.
 */
public interface UserLoginEventRepository extends MongoRepository<UserLoginEvent, String> {

}

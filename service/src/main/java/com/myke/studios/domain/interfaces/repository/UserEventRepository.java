package com.myke.studios.domain.interfaces.repository;

import com.myke.studios.userevent.register.UserRegisterEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * User Event repository with mongodb.
 */
public interface UserEventRepository extends MongoRepository<UserRegisterEvent, String> {

}

package com.myke.studios.domain.interfaces;

import com.myke.studios.PokemonEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Pokemon event repository.
 */
public interface PokemonEventRepository extends MongoRepository<PokemonEvent, String> {

}

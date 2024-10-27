package com.myke.studios.domain.interfaces;

import com.myke.studios.domain.entity.Pokemon;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Pokemon repository.
 */
public interface PokemonRepository  extends MongoRepository<Pokemon, String> {
}

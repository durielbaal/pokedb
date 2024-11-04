package com.myke.studios.domain.interfaces;

import com.myke.studios.domain.entity.PokemonEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Pokemon entity repository.
 */
public interface PokemonEntityRepository extends MongoRepository<PokemonEntity, String> {
}

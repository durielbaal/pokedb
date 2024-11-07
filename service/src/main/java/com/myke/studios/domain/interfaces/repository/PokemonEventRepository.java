package com.myke.studios.domain.interfaces.repository;

import com.myke.studios.pokemonevent.insert.PokemonInsertEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Pokemon event repository.
 */
public interface PokemonEventRepository extends MongoRepository<PokemonInsertEvent, String> {

}

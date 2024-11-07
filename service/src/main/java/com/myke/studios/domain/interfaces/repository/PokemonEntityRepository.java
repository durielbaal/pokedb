package com.myke.studios.domain.interfaces.repository;

import com.myke.studios.domain.entity.PokemonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Pokemon entity repository.
 */
public interface PokemonEntityRepository extends JpaRepository<PokemonEntity, String> {
}

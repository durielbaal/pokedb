package com.myke.studios.domain.interfaces.repository;

import com.myke.studios.domain.entity.PokemonEntity;
import java.util.UUID;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

/**
 * Pokemon entity repository.
 */
@Repository
public interface PokemonEntityRepository extends R2dbcRepository<PokemonEntity, UUID> {
}

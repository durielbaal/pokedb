package com.myke.studios.application.service;

import com.myke.studios.domain.entity.PokemonEntity;
import com.myke.studios.domain.interfaces.repository.PokemonEntityRepository;
import com.myke.studios.domain.interfaces.repository.PokemonEventRepository;
import com.myke.studios.domain.output.PokedbOutputPort;
import com.myke.studios.pokemonevent.insert.PokemonInsertEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Pokemon database service.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PokedbService implements PokedbOutputPort {

  /**
   * Pokemon entity repository.
   */
  private final PokemonEntityRepository pokemonEntityRepository;
  /**
   * Pokemon event repository.
   */
  private final PokemonEventRepository pokemonEventRepository;

  /**
   * Database event and pokemon.
   * @param pokemonInsertEvent in data base format, as data package(header and body).
   */
  @Override
  public void savePokemonEntity(PokemonInsertEvent pokemonInsertEvent) {
    pokemonEntityRepository.save(PokemonEntity.fromObjectToEntity(pokemonInsertEvent))
        .doOnSuccess(savedPokemon -> log.info("pokemonEntity saved correctly: {}",savedPokemon))
        .doOnError(e -> log.error("An error ocurred during pokemonEntity save: {}",e.getMessage()))
        .subscribe();

    pokemonEventRepository.save(pokemonInsertEvent);
    log.info("{} saved correctly: ", pokemonInsertEvent.getHeader().getEventType());
  }


}

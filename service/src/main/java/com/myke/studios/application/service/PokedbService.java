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
   *  Store pokemon in mongodb.
   * @param pokemonInsertEvent in data base format, as entity.
   */
  @Override
  public void savePokemonEntity(PokemonInsertEvent pokemonInsertEvent) {
    pokemonEntityRepository.save(PokemonEntity.fromObjectToEntity(pokemonInsertEvent));
    log.info("pokemonEntity saved correctly");
    pokemonEventRepository.save(pokemonInsertEvent);
    log.info("{} saved correctly: ", pokemonInsertEvent.getHeader().getEventType());
  }


}

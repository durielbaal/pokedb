package com.myke.studios.application.service;

import com.myke.studios.PokemonEvent;
import com.myke.studios.domain.entity.PokemonEntity;
import com.myke.studios.domain.interfaces.PokemonEntityRepository;
import com.myke.studios.domain.interfaces.PokemonEventRepository;
import com.myke.studios.domain.output.PokedbOutputPort;
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
   * @param pokemonEvent in data base format, as entity.
   */
  @Override
  public void savePokemonEntity(PokemonEvent pokemonEvent,PokemonEntity pokemonEntity) {
    pokemonEntityRepository.save(pokemonEntity);
    log.info("saved correctly: {}", pokemonEntity.toString());
    pokemonEventRepository.save(pokemonEvent);
    log.info("saved correctly: {}", pokemonEvent.toString());
  }


}

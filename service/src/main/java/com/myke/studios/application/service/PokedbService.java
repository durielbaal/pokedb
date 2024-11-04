package com.myke.studios.application.service;

import com.myke.studios.domain.entity.PokemonEntity;
import com.myke.studios.domain.interfaces.PokemonRepository;
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
   * Pokemon repository.
   */

  private final PokemonRepository pokemonRepository;

  /**
   *  Store pokemon in mongodb.
   * @param pokemonEntity in data base format, as entity.
   */
  @Override
  public void saveData(PokemonEntity pokemonEntity) {
    pokemonRepository.save(pokemonEntity);
    log.info("saved correctly: {}", pokemonEntity.toString());
  }


}

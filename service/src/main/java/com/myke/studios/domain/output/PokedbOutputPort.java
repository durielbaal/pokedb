package com.myke.studios.domain.output;

import com.myke.studios.PokemonEvent;
import com.myke.studios.domain.entity.PokemonEntity;

/**
 * Output port contract between service and controller.
 */
public interface PokedbOutputPort {

  /**
   * Save pokemon data.
   * @param pokemonEvent in data base format, as data package(header and body).
   * @param pokemonEntity body of data.
   */
  void savePokemonEntity(PokemonEvent pokemonEvent,PokemonEntity pokemonEntity);

}

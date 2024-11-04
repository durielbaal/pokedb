package com.myke.studios.domain.output;

import com.myke.studios.domain.entity.PokemonEntity;

/**
 * Output port contract between service and controller.
 */
public interface PokedbOutputPort {

  /**
   * Save pokemon data.
   * @param pokemonEntity in data base format, as entity.
   */
  void saveData(PokemonEntity pokemonEntity);
}

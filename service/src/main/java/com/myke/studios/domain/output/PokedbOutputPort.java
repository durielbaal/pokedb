package com.myke.studios.domain.output;

import com.myke.studios.pokemonevent.insert.PokemonInsertEvent;

/**
 * Output port contract between service and controller.
 */
public interface PokedbOutputPort {

  /**
   * Save pokemon data.
   * @param pokemonInsertEvent in data base format, as data package(header and body).
   */
  void savePokemonEntity(PokemonInsertEvent pokemonInsertEvent);

}

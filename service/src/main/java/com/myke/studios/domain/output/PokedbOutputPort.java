package com.myke.studios.domain.output;

import com.myke.studios.domain.entity.Pokemon;

/**
 * Output port contract between service and controller.
 */
public interface PokedbOutputPort {

  /**
   * Save pokemon data.
   * @param pokemon in data base format, as entity.
   */
  void saveData(Pokemon pokemon);
}

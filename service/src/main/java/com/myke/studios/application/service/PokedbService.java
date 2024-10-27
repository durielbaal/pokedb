package com.myke.studios.application.service;

import com.myke.studios.domain.entity.Pokemon;
import com.myke.studios.domain.interfaces.PokemonRepository;
import com.myke.studios.domain.output.PokedbOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Pokemon database service.
 */
@Service
public class PokedbService implements PokedbOutputPort {

  /**
   * Pokemon repository.
   */
  @Autowired
  private PokemonRepository pokemonRepository;

  /**
   *  Store pokemon in mongodb.
   * @param pokemon in data base format, as entity.
   */
  @Override
  public void saveData(Pokemon pokemon) {
    pokemonRepository.save(pokemon);
    System.out.println("Se ha guardado:" + pokemon.toString());
  }


}

package com.myke.studios.application.service;

import com.myke.studios.domain.entity.Pokemon;
import com.myke.studios.domain.output.PokedbOutputPort;
import org.springframework.stereotype.Service;

/**
 * Pokemon database service.
 */
@Service
public class PokedbService implements PokedbOutputPort {

  @Override
  public void saveData(Pokemon pokemon) {
    System.out.println("Se ha guardado:" + pokemon.toString());
  }


}

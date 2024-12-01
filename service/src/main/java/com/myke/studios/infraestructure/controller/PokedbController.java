package com.myke.studios.infraestructure.controller;

import com.myke.studios.constant.ConstantEvent;
import com.myke.studios.domain.output.PokedbOutputPort;
import com.myke.studios.pokemonevent.insert.PokemonInsertEvent;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

/**
 * Pokemon database controller.
 */
@Data
@RestController
@RequiredArgsConstructor
public class PokedbController {

  /**
   * instance of poke database output port interface.
   */
  private final PokedbOutputPort pokedbOutputPort;

  /**
   * save data when pokeapi send the message.
   * @param pokemonInsertEvent .
   */
  @KafkaListener(topics = "event.PokemonInsertEvent", groupId = ConstantEvent.POKE_INSERT_GROUP)
  public void consume(PokemonInsertEvent pokemonInsertEvent) {
    pokedbOutputPort.savePokemonEntity(pokemonInsertEvent);
  }
}

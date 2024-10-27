package com.myke.studios.infraestructure.controller;

import com.myke.studios.domain.entity.Pokemon;
import com.myke.studios.domain.output.PokedbOutputPort;
import com.myke.studios.infraestructure.dto.PokemonDto;
import lombok.Builder;
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
   * @param pkmn .
   */
  @KafkaListener(topics = "pokemon-topic", groupId = "pokedb-group")
  public void saveData(PokemonDto pkmn) {
    Pokemon pkmndb = Pokemon.builder()
        .id(pkmn.getId())
        .name(pkmn.getName()).build();
    pokedbOutputPort.saveData(pkmndb);
  }
}

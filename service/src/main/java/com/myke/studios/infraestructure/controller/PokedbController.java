package com.myke.studios.infraestructure.controller;

import com.myke.studios.PokemonEvent;
import com.myke.studios.domain.entity.PokemonEntity;
import com.myke.studios.domain.output.PokedbOutputPort;
import com.myke.studios.infraestructure.dto.PokemonDto;
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
   * @param pokemonEvent .
   */
  @KafkaListener(topics = "INSERT", groupId = "pokedb-group")
  public void consume(PokemonEvent pokemonEvent) {
    PokemonDto pokemonDto = pokemonEvent.getBody();
    PokemonEntity pokemonEntity = PokemonEntity.builder()
        .id(pokemonDto.getId())
        .name(pokemonDto.getName()).build();
    pokedbOutputPort.savePokemonEntity(pokemonEvent,pokemonEntity);
  }
}

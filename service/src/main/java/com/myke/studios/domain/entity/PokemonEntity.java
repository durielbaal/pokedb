package com.myke.studios.domain.entity;

import com.myke.studios.pokemonevent.insert.PokemonInsertEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Pokemon entity to manage in database (mongodb).
 */
@Document(collection = "pokemonDataCollection")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PokemonEntity {

  /**
   * id.
   */
  @Id
  private String id;
  /**
   * name.
   */
  private String name;

  /**
   * Get pokemonEntity through pokemonInsertEvent.
   * @param pokemonInsertEvent .
   * @return  PokemonInsertEvent.
   */
  public static PokemonEntity fromObjectToEntity(PokemonInsertEvent pokemonInsertEvent) {
    return PokemonEntity.builder()
        .id(pokemonInsertEvent.getBody().id)
        .name(pokemonInsertEvent.getBody().name).build();
  }
}

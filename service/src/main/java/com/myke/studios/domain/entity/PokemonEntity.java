package com.myke.studios.domain.entity;

import com.myke.studios.pokemonevent.insert.PokemonInsertEvent;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;




/**
 * Pokemon entity to manage in database (mongodb).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Pokemon")
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

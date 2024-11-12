package com.myke.studios.domain.entity;

import com.myke.studios.pokemonevent.insert.PokemonInsertEvent;
import jakarta.persistence.GeneratedValue;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


/**
 * Pokemon entity to manage in database (mongodb).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("Pokemon")
public class PokemonEntity {

  /**
   * id.
   */
  @Id
  private UUID id;
  /**
   * pokedex number.
   */
  @Column("pokedex_number")
  private String pokedexNumber;
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
        .pokedexNumber(pokemonInsertEvent.getBody().pokedexNumber)
        .name(pokemonInsertEvent.getBody().name).build();
  }
}

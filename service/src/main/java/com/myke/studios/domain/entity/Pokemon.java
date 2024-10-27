package com.myke.studios.domain.entity;

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
public class Pokemon {

  /**
   * id.
   */
  @Id
  private String id;
  /**
   * name.
   */
  private String name;
}

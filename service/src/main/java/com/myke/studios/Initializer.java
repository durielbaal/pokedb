package com.myke.studios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/**
 * Pokemon data base  management Initializer.
 */
@EnableR2dbcRepositories
@SpringBootApplication
public class Initializer {
  /**
   * Main method to start initializer.
   * @param args arguments.
   */
  public static void main(String[] args) {
    SpringApplication.run(Initializer.class, args);
  }
}

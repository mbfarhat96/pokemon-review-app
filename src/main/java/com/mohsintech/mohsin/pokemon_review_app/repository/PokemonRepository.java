package com.mohsintech.mohsin.pokemon_review_app.repository;

import com.mohsintech.mohsin.pokemon_review_app.models.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {

}

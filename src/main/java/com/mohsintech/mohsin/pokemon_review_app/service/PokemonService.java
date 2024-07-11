package com.mohsintech.mohsin.pokemon_review_app.service;

import com.mohsintech.mohsin.pokemon_review_app.dto.PokemonDto;
import com.mohsintech.mohsin.pokemon_review_app.dto.PokemonResponse;

public interface PokemonService {

    PokemonDto createPokemon(PokemonDto pokemonDto);

    PokemonDto getPokemon(int pokemonId);

    PokemonResponse getPokemons(int pageNo, int pageSize);

    PokemonDto updatePokemon(int pokemonId, PokemonDto pokemonDto);

    void deletePokemon(int pokemonId);


}

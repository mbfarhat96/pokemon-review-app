package com.mohsintech.mohsin.pokemon_review_app.service.impl;

import com.mohsintech.mohsin.pokemon_review_app.dto.PokemonDto;
import com.mohsintech.mohsin.pokemon_review_app.dto.PokemonResponse;
import com.mohsintech.mohsin.pokemon_review_app.exception.PokemonNotFoundException;
import com.mohsintech.mohsin.pokemon_review_app.models.Pokemon;
import com.mohsintech.mohsin.pokemon_review_app.repository.PokemonRepository;
import com.mohsintech.mohsin.pokemon_review_app.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonServiceImpl implements PokemonService {
    private final PokemonRepository pokemonRepository;

    @Autowired
    public PokemonServiceImpl(PokemonRepository pokemonRepository){
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public PokemonDto createPokemon(PokemonDto pokemonDto) {
        Pokemon pokemon = mapToEntity(pokemonDto);
        Pokemon newPokemon = pokemonRepository.save(pokemon);

        return mapToDto(newPokemon);
    }

    @Override
    public PokemonDto getPokemon(int pokemonId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() ->
                new PokemonNotFoundException("Pokemon Not Found"));
        return mapToDto(pokemon);
    }

    @Override
    public PokemonResponse getPokemons(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Pokemon> pokemons = pokemonRepository.findAll(pageable);
        List<Pokemon> listOfPokemon = pokemons.getContent();

        List<PokemonDto> content = listOfPokemon.stream().map(p -> mapToDto(p)).collect(Collectors.toList());

        PokemonResponse pokemonResponse = new PokemonResponse();
        pokemonResponse.setContent(content);
        pokemonResponse.setPageNo(pokemons.getNumber());
        pokemonResponse.setPageSize(pokemons.getSize());
        pokemonResponse.setTotalPages(pokemons.getTotalPages());
        pokemonResponse.setTotalElements(pokemons.getTotalElements());
        pokemonResponse.setLast(pokemons.isLast());

        return pokemonResponse;
    }

    @Override
    public PokemonDto updatePokemon( int pokemonId, PokemonDto pokemonDto) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() ->
                new PokemonNotFoundException("Pokemon Not Found"));
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());

        Pokemon updatedPokemon = pokemonRepository.save(pokemon);
        return mapToDto(updatedPokemon);
    }

    @Override
    public void deletePokemon(int pokemonId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() ->
                new PokemonNotFoundException("Pokemon Not Found"));
        pokemonRepository.delete(pokemon);
    }


    private Pokemon mapToEntity(PokemonDto pokemonDto){
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());
        return pokemon;
    }
    private PokemonDto mapToDto(Pokemon pokemon){
        PokemonDto pokemonDto = new PokemonDto();
        pokemonDto.setId(pokemon.getId());
        pokemonDto.setName(pokemon.getName());
        pokemonDto.setType(pokemon.getType());
        return pokemonDto;
    }


}

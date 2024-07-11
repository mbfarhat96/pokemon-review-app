package com.mohsintech.mohsin.pokemon_review_app.controller;

import com.mohsintech.mohsin.pokemon_review_app.dto.PokemonDto;
import com.mohsintech.mohsin.pokemon_review_app.dto.PokemonResponse;
import com.mohsintech.mohsin.pokemon_review_app.models.Pokemon;
import com.mohsintech.mohsin.pokemon_review_app.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/")
public class PokemonController {
    private final PokemonService pokemonService;

    @Autowired
    public PokemonController(PokemonService pokemonService){
        this.pokemonService = pokemonService;
    }

    @PostMapping("pokemon/create")
    public ResponseEntity<PokemonDto> createPokemon(@RequestBody PokemonDto pokemonDto){
        PokemonDto response = pokemonService.createPokemon(pokemonDto);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping("pokemon")
    public ResponseEntity<PokemonResponse> getPokemons(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false)int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false)int pageSize){
        PokemonResponse response = pokemonService.getPokemons(pageNo,pageSize);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("pokemon/{pokemonId}")
    public ResponseEntity<PokemonDto> getPokemon(@PathVariable(value = "pokemonId")int pokemonId){
        PokemonDto response = pokemonService.getPokemon(pokemonId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("pokemon/{pokemonId}/update")
    public ResponseEntity<PokemonDto> updatePokemon(@PathVariable(value = "pokemonId")int pokemonId, @RequestBody PokemonDto pokemonDto){
        PokemonDto response = pokemonService.updatePokemon(pokemonId, pokemonDto);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("pokemon/{pokemonId}/delete")
    public ResponseEntity<String> deletePokemon(@PathVariable(value = "pokemonId")int pokemonId){
        pokemonService.deletePokemon(pokemonId);
        return new ResponseEntity<>("Pokemon Deleted Successfully",HttpStatus.OK);
    }





}

package com.mohsintech.mohsin.pokemon_review_app.exception;

public class PokemonNotFoundException extends RuntimeException{

    public PokemonNotFoundException(String message){
        super(message);
    }

}

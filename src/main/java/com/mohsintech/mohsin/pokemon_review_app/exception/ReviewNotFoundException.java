package com.mohsintech.mohsin.pokemon_review_app.exception;

public class ReviewNotFoundException extends RuntimeException{

    public ReviewNotFoundException(String message){
        super(message);
    }
}

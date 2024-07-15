package com.mohsintech.mohsin.pokemon_review_app.dto;

import lombok.Data;

@Data
public class AuthResponseDto {
    private String accessToken;
    private String tokenType = "Bearer ";


    public AuthResponseDto(String accessToken){
        this.accessToken = accessToken;
    }
}

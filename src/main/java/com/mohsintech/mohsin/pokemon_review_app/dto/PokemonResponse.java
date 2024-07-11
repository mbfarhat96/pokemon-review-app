package com.mohsintech.mohsin.pokemon_review_app.dto;

import com.mohsintech.mohsin.pokemon_review_app.models.Pokemon;
import lombok.Data;

import java.util.List;

@Data
public class PokemonResponse {
    private List<PokemonDto> content;
    private int pageNo;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private boolean last;

}

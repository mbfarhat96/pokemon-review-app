package com.mohsintech.mohsin.pokemon_review_app.service;

import com.mohsintech.mohsin.pokemon_review_app.dto.ReviewDto;

import java.util.List;

public interface ReviewService {

    ReviewDto createReview(ReviewDto reviewDto, int pokemonId);

    List<ReviewDto> getReviewByPokemonId(int pokemonId);

    ReviewDto getReview(int pokemonId, int reviewId);

    ReviewDto updateReview(ReviewDto reviewDto, int pokemonId, int reviewId);

    void deleteReview(int pokemonid, int reviewId);


}

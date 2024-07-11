package com.mohsintech.mohsin.pokemon_review_app.service.impl;

import com.mohsintech.mohsin.pokemon_review_app.dto.ReviewDto;
import com.mohsintech.mohsin.pokemon_review_app.exception.PokemonNotFoundException;
import com.mohsintech.mohsin.pokemon_review_app.exception.ReviewNotFoundException;
import com.mohsintech.mohsin.pokemon_review_app.models.Pokemon;
import com.mohsintech.mohsin.pokemon_review_app.models.Review;
import com.mohsintech.mohsin.pokemon_review_app.repository.PokemonRepository;
import com.mohsintech.mohsin.pokemon_review_app.repository.ReviewRepository;
import com.mohsintech.mohsin.pokemon_review_app.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final PokemonRepository pokemonRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, PokemonRepository pokemonRepository){
        this.reviewRepository = reviewRepository;
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public ReviewDto createReview(ReviewDto reviewDto, int pokemonId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() ->
                new PokemonNotFoundException("Pokemon Not Found"));
        Review review = new Review();
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        review.setPokemon(pokemon);
        Review newReview = reviewRepository.save(review);
        return mapToDto(newReview);
    }

    @Override
    public List<ReviewDto> getReviewByPokemonId(int pokemonId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() ->
                new PokemonNotFoundException("Pokemon Not Found."));
        List<Review> reviews = reviewRepository.findByPokemonId(pokemonId);
        if (reviews.isEmpty()){
            throw new ReviewNotFoundException("Reviews For this Pokemon Weren't Found");
        }
        List<ReviewDto> reviewDtos = reviews.stream().map(review -> mapToDto(review)).collect(Collectors.toList());
        return reviewDtos;
    }

    @Override
    public ReviewDto getReview(int pokemonId, int reviewId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() ->
                new PokemonNotFoundException("Pokemon Not Found."));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
                new ReviewNotFoundException("Review Not Found"));
        if (pokemon.getId() != review.getPokemon().getId()){
            throw new ReviewNotFoundException("This Review Doesn't Exist For This Pokemon");
        }
        return mapToDto(review);
    }

    @Override
    public ReviewDto updateReview(ReviewDto reviewDto, int pokemonId, int reviewId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() ->
                new PokemonNotFoundException("Pokemon Not Found."));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
                new PokemonNotFoundException("Review Not Found"));
        if (pokemon.getId() != review.getPokemon().getId()){
            throw new ReviewNotFoundException("This Review Doesn't Exist For This Pokemon");
        }
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        Review updatedReview = reviewRepository.save(review);
        return mapToDto(updatedReview);
    }

    @Override
    public void deleteReview(int pokemonId, int reviewId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() ->
                new PokemonNotFoundException("Pokemon Not Found."));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
                new ReviewNotFoundException("Review Not Found"));
        if (pokemon.getId() != review.getPokemon().getId()){
            throw new PokemonNotFoundException("This Review Doesn't Exist For This Pokemon");
        }
        reviewRepository.delete(review);
    }

    private Review mapToEntity(ReviewDto reviewDto){
        Review review = new Review();
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        return review;
    }

    private ReviewDto mapToDto(Review review){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setContent(review.getContent());
        reviewDto.setStars(review.getStars());

        return reviewDto;
    }
}
